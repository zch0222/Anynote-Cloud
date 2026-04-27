## ADDED Requirements

### Requirement: 中间件编排文件

系统必须提供 `docker-compose-middleware.yaml`，包含以下服务：MySQL 8.0.42、Redis 6、Elasticsearch 8.7、RocketMQ 5.x（NameServer + Broker）、MinIO、Nacos 2.x（standalone + MySQL 存储）、XXL-Job Admin。所有服务必须加入自定义网络 `anynote-net`，并定义数据卷持久化。

#### Scenario: 一条命令启动全部中间件

- **WHEN** 执行 `docker compose -f docker-compose-middleware.yaml up -d`
- **THEN** 7 项中间件服务全部以守护进程模式启动，`docker compose ps` 显示所有服务状态为 `running` 或 `healthy`

#### Scenario: 中间件数据持久化

- **WHEN** 执行 `docker compose -f docker-compose-middleware.yaml restart`
- **THEN** MySQL 数据库中的数据、Redis 持久化文件、Elasticsearch 索引、MinIO 对象均保持不变

#### Scenario: Nacos 依赖 MySQL 就绪后启动

- **WHEN** MySQL 容器尚未通过健康检查
- **THEN** Nacos 容器不启动（`depends_on: condition: service_healthy`）

---

### Requirement: MySQL 自动初始化

MySQL 容器首次启动时必须自动执行 `sql/` 目录下的所有初始化 SQL 文件，完成数据库 schema 和基础数据导入，无需人工介入。

#### Scenario: 首次启动自动建库建表

- **WHEN** MySQL 容器为首次启动（数据卷为空）
- **THEN** 执行 `sql/anynote.sql`、`sql/anynote_config.sql`、`sql/anynote_xxl_job.sql` 等文件后，数据库 `anynote` 中的表结构完整创建

#### Scenario: 非首次启动跳过初始化

- **WHEN** MySQL 数据卷已存在数据（非首次启动）
- **THEN** 初始化脚本不重复执行，已有数据不被覆盖

---

### Requirement: 全量部署编排文件

系统必须提供 `docker-compose.yaml`，在中间件基础上增加全部应用服务：`anynote-gateway`、`anynote-auth`、`anynote-admin`，以及 `anynote-modules-*` 下的全部业务模块。应用服务必须依赖 Nacos 健康检查通过后再启动。

#### Scenario: 应用服务等待 Nacos 就绪

- **WHEN** Nacos 容器尚未通过健康检查（HTTP `/nacos/v1/console/health/readiness` 返回非 200）
- **THEN** 所有应用服务容器不进入启动流程

#### Scenario: 全量一键部署

- **WHEN** 执行 `docker compose up -d`（JAR 文件已预先构建）
- **THEN** 所有中间件和应用服务容器启动，Gateway 在端口 8080 可响应请求

---

### Requirement: 环境变量统一管理

系统必须提供 `.env.example` 文件，声明所有可配置参数（镜像版本、端口映射、数据库密码、MinIO 密钥等）。实际部署时复制为 `.env` 并按需修改，Compose 文件通过 `${VAR}` 语法引用。

#### Scenario: 修改默认端口不影响服务互联

- **WHEN** 在 `.env` 中修改 `GATEWAY_PORT=9090`
- **THEN** 宿主机通过 9090 端口访问 Gateway，容器间通信仍使用内部服务名和固定端口，服务正常

#### Scenario: 缺少 .env 文件时给出提示

- **WHEN** 执行 `docker compose up` 时 `.env` 文件不存在
- **THEN** Docker Compose 使用 `.env.example` 中的默认值（或 Compose 内 `default` 值），并在日志中提示使用了默认配置

---

### Requirement: 应用服务 Dockerfile

每个应用服务（`anynote-auth`、`anynote-gateway`、`anynote-admin`、`anynote-modules-*`）必须提供 `Dockerfile`，支持多阶段构建：第一阶段使用 Maven 编译，第二阶段使用最小 JRE 镜像运行，最终镜像不包含源码或 Maven 本地仓库。

#### Scenario: 多阶段构建产物体积合理

- **WHEN** 执行 `docker build` 构建某服务镜像
- **THEN** 最终镜像大小不超过 300 MB（不含基础镜像层）

#### Scenario: 本地预编译 JAR 快速构建

- **WHEN** 宿主机已执行 `./mvnw clean package -DskipTests` 且 JAR 存在
- **THEN** 使用 `Dockerfile.local`（仅 COPY JAR）构建，跳过 Maven 编译阶段，构建时间 < 30 秒

---

### Requirement: 应用服务配置覆盖

应用服务容器必须通过环境变量覆盖 `bootstrap.yml` 中的 Nacos 地址、数据库地址等连接配置，使其指向 Docker 网络内的服务名，而不修改任何源码配置文件。

#### Scenario: 容器内 Nacos 地址覆盖

- **WHEN** 应用服务容器启动，设置环境变量 `SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=nacos:8848`
- **THEN** 应用成功注册到容器内 Nacos，`bootstrap.yml` 中的 `localhost:8848` 不生效

#### Scenario: 本地开发不受影响

- **WHEN** 开发者在宿主机直接运行 `./mvnw spring-boot:run`（未设置覆盖环境变量）
- **THEN** 应用使用 `bootstrap.yml` 中的原始配置连接本地 `localhost:8848`，行为不变

---

### Requirement: 健康检查定义

所有中间件容器必须定义 `healthcheck`，应用服务容器必须定义 Spring Boot Actuator 端点健康检查。健康检查失败时容器不被标记为 `healthy`，上游依赖容器不提前启动。

#### Scenario: MySQL 健康检查

- **WHEN** MySQL 容器启动
- **THEN** 健康检查通过 `mysqladmin ping` 命令验证，失败时 `docker compose ps` 显示 `unhealthy`

#### Scenario: 应用服务健康检查

- **WHEN** 应用服务容器启动完成
- **THEN** 健康检查访问 `/actuator/health`，返回 200 则标记为 `healthy`
