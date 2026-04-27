## Context

Anynote Cloud 是一个 Spring Cloud Alibaba 微服务项目，依赖 7 项基础中间件（MySQL、Redis、Elasticsearch、RocketMQ、MinIO、Nacos、XXL-Job）和 10+ 个应用服务。当前项目不含任何容器化配置，开发者需手动搭建全部依赖，上手成本高。本设计在不修改任何 Java 业务代码的前提下，引入 Docker Compose 编排方案。

## Goals / Non-Goals

**Goals:**
- 提供 `docker-compose-middleware.yaml`，一条命令拉起全部中间件（供本地开发使用）。
- 提供 `docker-compose.yaml`，完整编排中间件 + 全部应用服务（供演示/集成测试使用）。
- 数据卷持久化，容器重启后数据不丢失。
- MySQL 容器首次启动时自动执行 `sql/*.sql` 完成 schema 初始化。
- Nacos 容器启动后自动导入应用配置（通过初始化脚本）。
- 统一的 `.env` 文件管理镜像版本、端口映射和密码等可变参数。

**Non-Goals:**
- 不提供 Kubernetes/Helm 方案。
- 不处理 HTTPS/TLS 终止（由外部反向代理负责）。
- 不提供生产级高可用配置（主从、集群）。
- 不修改任何 Java 代码或 Maven 构建逻辑。

## Decisions

### 决策 1：拆分为两个 Compose 文件

**选择**：`docker-compose-middleware.yaml`（中间件）+ `docker-compose.yaml`（全量，通过 `extends` 或直接包含中间件）。

**理由**：日常开发时开发者通常只需要中间件，应用服务在 IDE 中以 `spring-boot:run` 启动。一个文件搞定所有场景会导致每次都启动不必要的容器。分文件让两种场景都能用最小代价启动。

**备选方案**：使用 Profiles（`--profile middleware`）——但 Compose V2 的 profile 语义对新用户不直观，拆文件更清晰。

---

### 决策 2：应用服务 Dockerfile 采用多阶段构建

**选择**：Stage 1 使用 `maven:3.8-openjdk-8` 编译打包；Stage 2 使用 `eclipse-temurin:8-jre-alpine` 运行，最终镜像 < 150 MB。

**理由**：单阶段构建会将 Maven 本地仓库（~500 MB）打入镜像，极大增加镜像体积和推送时间。多阶段构建只保留运行时所需 JAR。

**备选方案**：先在宿主机执行 `./mvnw clean package`，Dockerfile 只 COPY 已编译的 JAR——简单但要求宿主机有 Java 8 + Maven 环境，不够自包含。最终提供两种方式均可：`docker-compose.yaml` 默认使用预编译 JAR（`Dockerfile.local`），CI 使用多阶段构建（`Dockerfile`）。

---

### 决策 3：Nacos 使用 standalone 模式 + 外挂 MySQL

**选择**：Nacos 2.x standalone 模式，数据存储指向同一 MySQL 实例的独立库 `nacos_config`。

**理由**：项目当前 Nacos namespace 配置已固定，standalone 模式足够开发和演示使用，避免引入集群复杂度。使用 MySQL 持久化而非嵌入式 Derby，保证配置在容器重建后不丢失。

---

### 决策 4：服务启动顺序通过 `depends_on` + `healthcheck` 控制

**选择**：所有中间件定义 `healthcheck`；应用服务使用 `depends_on: condition: service_healthy` 等待依赖就绪。

**理由**：Nacos 注册中心未就绪时应用服务会启动失败。`depends_on` 仅控制容器启动顺序，不能保证服务真正可用，必须结合 `healthcheck`。

**启动顺序**：MySQL → Redis → Nacos（依赖 MySQL）→ Elasticsearch、RocketMQ NameServer → RocketMQ Broker（依赖 NameServer）→ MinIO、XXL-Job → 应用服务（依赖 Nacos）。

---

### 决策 5：网络隔离

**选择**：所有容器加入同一自定义 bridge 网络 `anynote-net`，禁用默认网络。

**理由**：自定义网络提供服务名 DNS 解析，`bootstrap.yml` 中使用服务名（如 `nacos`、`mysql`）替代 `localhost`，与 Docker 环境天然对齐。

---

### 决策 6：配置差异化管理

**选择**：应用服务通过环境变量 `SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=nacos:8848` 覆盖 `bootstrap.yml` 中的默认值，无需修改源码中的配置文件。

**理由**：Spring Boot 环境变量覆盖优先级高于 `bootstrap.yml`，无侵入性。开发者本地运行时仍使用原有 `localhost:8848` 配置不受影响。

## Risks / Trade-offs

| 风险 | 缓解措施 |
|------|----------|
| Nacos 初始配置导入复杂（需要在容器启动后通过 API 批量 import） | 提供 `docker/nacos-init.sh` 脚本，在 Nacos 健康后自动调用 Open API 导入配置；文档说明手动导入备选步骤 |
| 全量启动（12+ 容器）内存占用高（预估 6-8 GB） | `docker-compose-middleware.yaml` 单独使用仅需 ~3 GB；应用服务按需按 profile 启动 |
| 多阶段构建首次编译慢（下载依赖 ~10 min） | 利用 Docker layer cache，`pom.xml` 单独 COPY 并在依赖变化前缓存 `mvn dependency:go-offline` |
| `bootstrap.yml` 硬编码 `localhost` 导致容器内解析失败 | 通过环境变量覆盖，无需修改代码；文档明确说明需覆盖的变量清单 |
| RocketMQ broker 地址注册为容器内网 IP，宿主机无法访问 | 设置 `brokerIP1` 为宿主机 IP 或使用 `host` 网络模式（仅限 Linux） |

## Migration Plan

1. 确认宿主机已安装 Docker Engine ≥ 20.x 及 `docker compose` V2 插件。
2. 复制 `.env.example` 为 `.env`，按需修改密码和端口。
3. 启动中间件：`docker compose -f docker-compose-middleware.yaml up -d`。
4. 等待 Nacos 健康，运行 `docker/nacos-init.sh` 导入初始配置。
5. （可选）完整启动：`docker compose up -d`（需先执行 `./mvnw clean package -DskipTests` 生成各模块 JAR）。
6. **回滚**：`docker compose down -v` 销毁全部容器和数据卷，重新执行上述步骤即可重建。

## Open Questions

- Nacos 初始配置（`application-dev.yml`等）是否可以从现有 Nacos 实例导出并提交到仓库？若有则可在 `docker/nacos-config/` 中直接挂载。
- XXL-Job Admin 是否需要与业务数据库共用同一 MySQL 实例？（当前设计复用，可通过 `.env` 切换为独立实例。）
- 应用服务镜像是否需要推送到私有镜像仓库？（当前设计仅本地 build，不涉及推送。）
