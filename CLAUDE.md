# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

See [AGENTS.md](./AGENTS.md) for full project structure, build commands, coding conventions, testing guidelines, and commit style.

## Build & Run

### Docker 部署

1. 复制环境变量示例：`cp .env.example .env`，按需修改端口、密码、`NACOS_NAMESPACE` 和 `ROCKETMQ_BROKER_IP`。
2. 启动中间件：`docker compose -f docker-compose-middleware.yaml up -d`。`nacos-init` 一次性容器会在 Nacos 健康后导入 `docker/nacos/configs/*.yml`。
3. 如需手动重跑 Nacos 配置导入：`NACOS_ADDR=http://localhost:8848 docker/nacos/nacos-init.sh`。
4. 完整部署前先构建 JAR：`./mvnw clean package -DskipTests`，再执行 `docker compose up -d --build`。
5. 只重建单个应用：`docker compose up -d --build anynote-gateway`。
6. 停止服务：`docker compose down`；清空数据卷重建：`docker compose down -v`。

更多说明见 [docker/README.md](./docker/README.md)。

## Additional Rules

- 代码注释使用中文。
