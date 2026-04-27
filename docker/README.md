# Docker 部署说明

## 前置条件

- Docker Engine 20.x 或更高版本。
- Docker Compose V2，即可使用 `docker compose` 命令。
- 全量应用部署建议预留 6 GB 以上内存；仅中间件模式建议预留 3 GB 以上内存。

## 环境变量

复制示例文件后再启动：

```bash
cp .env.example .env
```

常用变量：

- `MYSQL_ROOT_PASSWORD`、`MYSQL_APP_USER`、`MYSQL_APP_PASSWORD`：MySQL root 与应用账号。
- `REDIS_PASSWORD`：Redis 密码，留空时不启用 Redis requirepass。
- `MINIO_ROOT_USER`、`MINIO_ROOT_PASSWORD`：MinIO 控制台账号。
- `NACOS_NAMESPACE`：应用 `bootstrap.yml` 使用的 Nacos namespace。
- `ROCKETMQ_BROKER_IP`：同时控制 RocketMQ 端口绑定地址和 Broker 注册地址，默认 `127.0.0.1` 仅本机访问。设为宿主机 IP 即可开放跨机器访问。
- `GATEWAY_PORT`、`AUTH_PORT`、`MYSQL_PORT` 等端口变量：只影响宿主机端口映射，容器间仍使用服务名和内部端口。

`.env` 已加入 `.gitignore`，不要提交真实密码。

## 启动中间件

```bash
docker compose -f docker-compose-middleware.yaml up -d
docker compose -f docker-compose-middleware.yaml ps
```

中间件包括 MySQL、Redis、Nacos、Elasticsearch、RocketMQ、MinIO、XXL-Job Admin。MySQL 首次启动时会运行 `docker/mysql/init/00-import-sql.sh`，按数据库分别导入 `sql/*.sql`。Nacos 健康后，`nacos-init` 一次性容器会导入 `docker/nacos/configs/*.yml`。

手动重跑 Nacos 导入：

```bash
NACOS_ADDR=http://localhost:8848 docker/nacos/nacos-init.sh
```

## 全量应用部署

默认使用 `Dockerfile.local`，需要先在宿主机生成 JAR：

```bash
./mvnw clean package -DskipTests
docker compose up -d --build
```

如需使用多阶段 Dockerfile 构建某个模块：

```bash
docker build --build-arg MODULE=anynote-gateway -t anynote/anynote-gateway:multi .
docker build --build-arg MODULE=anynote-modules/anynote-modules-system -t anynote/anynote-modules-system:multi .
```

## 常用命令

```bash
docker compose ps
docker compose logs -f anynote-gateway
docker compose up -d --build anynote-gateway
docker compose restart nacos
docker compose down
docker compose down -v
```

`docker compose down -v` 会删除 MySQL、Redis、Elasticsearch、RocketMQ、MinIO 等数据卷，下次启动会重新执行 MySQL 初始化。
