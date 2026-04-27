## Why

Anynote Cloud 依赖 Nacos、MySQL、Redis、Elasticsearch、RocketMQ、MinIO、XXL-Job 等多项中间件，新开发者或 CI 环境需要手动逐一安装和配置，启动成本极高。通过提供 `docker-compose.yaml`，实现一条命令拉起全部基础设施与应用服务，大幅降低本地开发和演示部署门槛。

## What Changes

- 新增 `docker-compose.yaml`：覆盖所有中间件（MySQL、Redis、Elasticsearch、RocketMQ、MinIO、Nacos、XXL-Job Admin）及全部应用服务（anynote-gateway、anynote-auth、anynote-admin、anynote-modules-*）。
- 新增 `docker-compose-middleware.yaml`：仅包含中间件，供只需本地启动中间件的开发者使用。
- 新增 `.env`：统一管理镜像版本、端口、密码等可变配置。
- 新增各服务 `Dockerfile`（多阶段构建），基于 Maven 编译后打包为最小运行镜像。
- 新增 `docker/` 目录，存放 Nacos 初始配置导入脚本、MySQL 初始化 SQL 挂载配置。
- 更新 `CLAUDE.md`，补充 Docker 部署说明。

## Capabilities

### New Capabilities

- `docker-compose-deployment`：完整的 Docker Compose 编排方案，包含中间件与应用服务的容器化定义、环境变量管理、数据卷持久化、健康检查及服务依赖顺序。

### Modified Capabilities

（无现有 spec 需要变更）

## Impact

- **新增文件**：`docker-compose.yaml`、`docker-compose-middleware.yaml`、`.env`、`docker/init/`、各模块 `Dockerfile`。
- **不影响现有业务代码**：无 Java 代码修改，无 API 契约变更。
- **配置影响**：`bootstrap.yml` 中的 Nacos 地址、数据库地址需与 Docker 网络服务名保持一致；`.env` 中提供默认值，生产环境需覆盖。
- **SQL 影响**：`docker/init/` 挂载 `sql/*.sql`，MySQL 容器首次启动时自动执行初始化。
- **外部依赖**：需安装 Docker Engine ≥ 20.x 及 Docker Compose V2（`docker compose` 命令）。
- **非目标**：不提供 Kubernetes/Helm 编排；不涉及 HTTPS/TLS 终止；不处理生产级高可用配置。
