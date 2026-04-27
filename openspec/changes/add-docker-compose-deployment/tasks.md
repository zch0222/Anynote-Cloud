## 1. 基础文件与目录结构

- [ ] 1.1 在项目根目录创建 `.env.example`，声明所有可配置变量（镜像版本、端口映射、MySQL/Redis/MinIO 密码、Nacos namespace 等）
- [ ] 1.2 创建 `docker/` 目录及子目录：`docker/mysql/init/`、`docker/nacos/`
- [ ] 1.3 将 `sql/*.sql` 文件通过符号链接或复制方式放入 `docker/mysql/init/`，确保 MySQL 容器首次启动时自动执行

## 2. 中间件 Compose 文件

- [ ] 2.1 编写 `docker-compose-middleware.yaml`：定义 `mysql`、`redis`、`nacos`、`elasticsearch`、`rocketmq-namesrv`、`rocketmq-broker`、`minio`、`xxl-job-admin` 服务
- [ ] 2.2 为每个中间件服务配置数据卷（`volumes`）实现持久化
- [ ] 2.3 为每个中间件服务定义 `healthcheck`（MySQL 用 `mysqladmin ping`，Redis 用 `redis-cli ping`，Nacos 用 HTTP readiness 接口，其余用 TCP 或 HTTP 探针）
- [ ] 2.4 设置 Nacos `depends_on: mysql: condition: service_healthy`
- [ ] 2.5 设置 RocketMQ Broker `depends_on: rocketmq-namesrv: condition: service_healthy`
- [ ] 2.6 定义统一自定义网络 `anynote-net`（bridge 模式）并在所有服务中引用
- [ ] 2.7 所有镜像版本、端口、密码通过 `.env` 中的变量引用（`${VAR:-default}`）
- [ ] 2.8 配置 RocketMQ Broker 的 `brokerIP1` 环境变量，解决宿主机访问问题

## 3. Nacos 初始配置导入

- [ ] 3.1 编写 `docker/nacos/nacos-init.sh`：等待 Nacos 健康后，通过 Nacos Open API 批量导入 `application-dev.yml` 等共享配置
- [ ] 3.2 在 `docker-compose-middleware.yaml` 中为 Nacos 服务挂载初始化脚本，或通过独立 `nacos-init` 一次性容器（`restart: no`）在 Nacos 健康后自动执行导入
- [ ] 3.3 在 `docker/nacos/` 目录中准备 Nacos 配置文件样例（`application-dev.yml.example`），说明需要填写的数据库地址、中间件地址等参数

## 4. 全量部署 Compose 文件

- [ ] 4.1 编写 `docker-compose.yaml`，在中间件基础上新增应用服务：`anynote-gateway`（8080）、`anynote-auth`（8083）、`anynote-admin`
- [ ] 4.2 在 `docker-compose.yaml` 中新增 `anynote-modules-system`、`anynote-modules-note`、`anynote-modules-file`、`anynote-modules-ai`、`anynote-modules-ai-nio`、`anynote-modules-manage`、`anynote-modules-notify`、`anynote-modules-job`、`anynote-modules-external-api` 应用服务
- [ ] 4.3 所有应用服务设置 `depends_on: nacos: condition: service_healthy`
- [ ] 4.4 所有应用服务通过环境变量覆盖连接配置：`SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=nacos:8848`、`SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR=nacos:8848`
- [ ] 4.5 为应用服务定义 Actuator 健康检查（`/actuator/health`）

## 5. 应用服务 Dockerfile

- [ ] 5.1 在项目根目录创建通用 `Dockerfile`（多阶段构建：Maven 编译 + JRE Alpine 运行），通过 `--build-arg MODULE=<module-name>` 参数指定构建目标模块
- [ ] 5.2 创建 `Dockerfile.local`（仅 COPY 预编译 JAR），用于宿主机已执行 `./mvnw package` 后的快速构建
- [ ] 5.3 在 `Dockerfile` 中优化 Maven 依赖缓存：先 COPY `pom.xml` 并执行 `dependency:go-offline`，再 COPY 源码
- [ ] 5.4 验证：对 `anynote-gateway` 执行 `docker build` 构建成功，镜像大小符合预期（< 300 MB）

## 6. 文档更新

- [ ] 6.1 更新 `CLAUDE.md`，在"Build & Run"章节补充 Docker 部署说明（中间件启动、Nacos 初始化、全量部署命令）
- [ ] 6.2 在项目根目录创建 `docker/README.md`（或在 `CLAUDE.md` 中说明），列出：前置条件、`.env` 配置说明、分步启动流程、常用管理命令（查看日志、重建单个服务等）

## 7. 端到端验证

- [ ] 7.1 执行 `docker compose -f docker-compose-middleware.yaml up -d`，确认所有中间件健康
- [ ] 7.2 运行 `docker/nacos/nacos-init.sh`，验证 Nacos 控制台中配置已导入
- [ ] 7.3 执行 `./mvnw clean package -DskipTests`，再运行 `docker compose up -d`，确认 Gateway 在 8080 端口响应请求
- [ ] 7.4 执行 `docker compose down -v` 后重新执行 2.1 步骤，验证数据卷清空后重建流程正常（MySQL 重新初始化）
- [ ] 7.5 修改 `.env` 中的 `GATEWAY_PORT`，验证端口映射生效且服务间通信不受影响
