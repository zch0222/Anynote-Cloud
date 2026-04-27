# Anynote Cloud

基于 Spring Cloud + Nacos 的云笔记微服务后端。

---

## 系统架构

### 整体架构图

```
外部流量
    │
    ▼
┌─────────────────────────────────────────────────────────┐
│  anynote-gateway  (Spring Cloud Gateway)  :8080         │
│  · 路由分发 · JWT 鉴权前置过滤 · 限流                     │
└────────────────────────┬────────────────────────────────┘
                         │ 服务发现（Nacos）
          ┌──────────────┼──────────────────┐
          ▼              ▼                  ▼
  ┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐
  │ anynote-auth │ │ anynote-admin│ │   业务微服务           │
  │   :8083      │ │   :8079      │ │  (见下方服务列表)      │
  └──────────────┘ └──────────────┘ └──────────────────────┘
          │                                  │
          └──────────────┬───────────────────┘
                         │ 共享中间件（anynote-net）
   ┌─────────────────────┼────────────────────────────────┐
   │  MySQL :3306        │  Redis :6379                   │
   │  Nacos :8848        │  Elasticsearch :9200           │
   │  RocketMQ :9876     │  MinIO :9000                   │
   │  XXL-Job Admin :10086                                │
   └────────────────────────────────────────────────────-─┘
```

### 服务清单

| 服务 | 模块 | 默认端口 | 职责 |
|------|------|----------|------|
| anynote-gateway | `anynote-gateway` | 8080 | API 网关、路由、JWT 校验 |
| anynote-auth | `anynote-auth` | 8083 | 认证授权、Token 签发 |
| anynote-admin | `anynote-admin` | 8079 | 运维管理控制台（Actuator 聚合等） |
| anynote-modules-system | `anynote-modules/anynote-modules-system` | 8091 | 用户、角色、权限、租户 |
| anynote-modules-note | `anynote-modules/anynote-modules-note` | 18091 | 笔记核心 CRUD、搜索 |
| anynote-modules-file | `anynote-modules/anynote-modules-file` | 8095 | 文件上传下载（MinIO） |
| anynote-modules-ai | `anynote-modules/anynote-modules-ai` | 9210 | AI 服务调用（同步） |
| anynote-modules-ai-nio | `anynote-modules/anynote-modules-ai-nio` | 9065 | AI 服务调用（SSE/异步） |
| anynote-modules-manage | `anynote-modules/anynote-modules-manage` | 18092 | 系统管理后台业务 |
| anynote-modules-notify | `anynote-modules/anynote-modules-notify` | 9066 | 通知与消息推送 |
| anynote-modules-job | `anynote-modules/anynote-modules-job` | 8093 | 定时任务执行器（XXL-Job） |
| anynote-modules-external-api | `anynote-modules/anynote-modules-external-api` | 8097 | 对外开放 API |

### 中间件依赖

| 中间件 | 用途 |
|--------|------|
| MySQL 8 | 主数据库（多库：anynote / anynote_config / anynote_xxl_job） |
| Redis | 会话缓存、限流、分布式锁 |
| Nacos | 服务注册发现 + 配置中心 |
| Elasticsearch | 全文搜索（笔记内容） |
| RocketMQ | 异步消息（通知、AI 任务） |
| MinIO | 对象存储（文件、图片） |
| XXL-Job Admin | 分布式定时任务调度 |

### 模块依赖关系

```
anynote-common/          ← 所有服务的公共库
  ├─ anynote-common-core
  ├─ anynote-common-security
  ├─ anynote-common-redis
  ├─ anynote-common-rocketmq
  ├─ anynote-common-elasticsearch
  └─ ...

anynote-api/             ← Feign 接口定义（被业务服务引用）
  ├─ anynote-api-system
  ├─ anynote-api-note
  ├─ anynote-api-file
  └─ ...

anynote-modules/         ← 业务服务（均依赖 anynote-common + anynote-api）
anynote-auth/            ← 认证服务
anynote-gateway/         ← 网关
anynote-admin/           ← 管理控制台
```

---

## Docker Compose 部署

### 前置条件

- Docker Engine ≥ 20.x
- Docker Compose V2（`docker compose` 命令可用）
- 中间件模式：建议 ≥ 3 GB 内存；全量部署：建议 ≥ 6 GB 内存

### 第一步：配置环境变量

```bash
cp .env.example .env
```

根据实际环境修改 `.env`，重点关注：

```dotenv
# 数据库密码（生产环境务必修改）
MYSQL_ROOT_PASSWORD=your_strong_password
MYSQL_APP_PASSWORD=your_app_password

# MinIO 密码
MINIO_ROOT_PASSWORD=your_minio_password

# RocketMQ Broker 对外 IP（宿主机 IP，非容器 IP）
ROCKETMQ_BROKER_IP=192.168.1.100   # 改为宿主机实际 IP

# Nacos namespace（与 bootstrap.yml 保持一致）
NACOS_NAMESPACE=0587fa28-1301-43db-a7a1-599c00fc3f70
```

### 第二步：启动中间件

```bash
docker compose -f docker-compose-middleware.yaml up -d
```

启动后自动完成：
- MySQL 首次建库并导入所有 SQL schema（`docker/mysql/init/`）
- `nacos-init` 一次性容器将 `docker/nacos/configs/*.yml` 导入 Nacos 配置中心

检查状态：

```bash
docker compose -f docker-compose-middleware.yaml ps
```

全部服务应显示 `(healthy)`，Nacos 约需 60 秒完成启动。

### 第三步：部署应用服务

**方式 A — 使用预编译 JAR（推荐，构建速度快）**

> 需要本地 Java 8 环境。项目使用 Lombok，暂不兼容 Java 21+。

```bash
./mvnw clean package -DskipTests
docker compose up -d --build
```

**方式 B — 使用多阶段 Dockerfile（无需本地 JDK）**

Docker 内部使用 Java 8 Maven 镜像完整编译，首次构建约需 5–15 分钟。

```bash
APP_DOCKERFILE=Dockerfile docker compose up -d --build
```

**重建单个服务：**

```bash
# 使用本地 JAR
docker compose up -d --build anynote-gateway

# 使用 Docker 内部编译
APP_DOCKERFILE=Dockerfile docker compose up -d --build anynote-gateway
```

### 验证部署

```bash
# 网关健康检查
curl http://localhost:8080/actuator/health

# Nacos 控制台（账号 nacos/nacos）
open http://localhost:8848/nacos

# MinIO 控制台
open http://localhost:9001

# XXL-Job Admin（账号 admin/123456）
open http://localhost:10086/xxl-job-admin
```

### 常用运维命令

```bash
# 查看所有服务状态
docker compose ps

# 实时查看某服务日志
docker compose logs -f anynote-gateway

# 重启某个服务
docker compose restart anynote-modules-note

# 仅停止服务（保留数据卷）
docker compose down

# 停止并清空所有数据卷（谨慎：数据会丢失）
docker compose down -v

# 重新导入 Nacos 配置（不重启其他服务）
docker compose -f docker-compose-middleware.yaml restart nacos-init
```

---

## 数据迁移

### 场景一：将已有数据库迁移到 Docker 环境

1. **导出原库数据**

   ```bash
   mysqldump -h <原数据库IP> -u root -p \
     --databases anynote anynote_config anynote_xxl_job \
     > backup_$(date +%Y%m%d).sql
   ```

2. **启动 Docker 中间件（首次，自动初始化 schema）**

   ```bash
   docker compose -f docker-compose-middleware.yaml up -d
   # 等待 MySQL healthy 后，schema 会被自动导入
   ```

3. **导入业务数据（覆盖 schema 已有内容）**

   ```bash
   # 将备份文件复制进容器并导入
   docker cp backup_20240101.sql anynote-mysql:/tmp/
   docker exec -e MYSQL_PWD=AnynoteRoot123 anynote-mysql \
     sh -c 'mysql -uroot < /tmp/backup_20240101.sql'
   ```

4. **迁移 MinIO 文件**（如果原来用的也是 MinIO）

   ```bash
   # 使用 mc（MinIO Client）同步
   mc alias set src http://<原MinIO地址> <accessKey> <secretKey>
   mc alias set dst http://localhost:9000 anynote AnynoteMinio123
   mc mirror src/<bucket> dst/<bucket>
   ```

   如果原来是本地文件系统，将文件目录挂载到 MinIO 数据卷或通过 mc 上传。

5. **更新 Nacos 配置**（数据库连接地址等）

   重新导入或在 Nacos 控制台手动修改 `application-dev.yml`，将数据库/Redis 等地址改为 Docker 服务名（`mysql`、`redis` 等）。

   ```bash
   # 重新执行 nacos-init
   docker compose -f docker-compose-middleware.yaml restart nacos-init
   ```

### 场景二：Docker 环境跨主机迁移

1. **备份数据卷**

   ```bash
   # 停止服务（保留卷）
   docker compose -f docker-compose-middleware.yaml down

   # 分别备份各数据卷
   docker run --rm \
     -v anynote_mysql-data:/data \
     -v $(pwd)/backup:/backup \
     alpine tar czf /backup/mysql-data.tar.gz -C /data .

   docker run --rm \
     -v anynote_minio-data:/data \
     -v $(pwd)/backup:/backup \
     alpine tar czf /backup/minio-data.tar.gz -C /data .

   # Elasticsearch 数据（体积可能较大）
   docker run --rm \
     -v anynote_elasticsearch-data:/data \
     -v $(pwd)/backup:/backup \
     alpine tar czf /backup/es-data.tar.gz -C /data .
   ```

2. **传输备份文件到新主机**

   ```bash
   scp backup/*.tar.gz user@new-host:/path/to/backup/
   ```

3. **在新主机恢复**

   ```bash
   # 先启动一次让 Compose 创建卷，然后停止
   docker compose -f docker-compose-middleware.yaml up -d --no-start

   # 恢复各卷数据
   docker run --rm \
     -v anynote_mysql-data:/data \
     -v /path/to/backup:/backup \
     alpine sh -c "cd /data && tar xzf /backup/mysql-data.tar.gz"

   # 同样恢复 minio、elasticsearch 等卷...

   # 启动
   docker compose -f docker-compose-middleware.yaml up -d
   ```

### 场景三：版本升级滚动更新

单个服务升级无需停全套：

```bash
# 1. 构建新镜像
docker build --build-arg MODULE=anynote-modules-note \
  -t anynote/anynote-modules-note:v2.0 .

# 2. 更新 .env 或 docker-compose.yaml 中的镜像版本

# 3. 滚动重启目标服务
docker compose up -d --build anynote-modules-note
```

如有数据库 schema 变更，需在启动新版本前手动执行变更脚本：

```bash
docker exec -e MYSQL_PWD=AnynoteRoot123 anynote-mysql \
  sh -c 'mysql -uroot anynote < /tmp/migration_v2.0.sql'
```

---

## 目录结构

```
Anynote-Cloud/
├── anynote-gateway/          # 网关服务
├── anynote-auth/             # 认证服务
├── anynote-admin/            # 管理控制台
├── anynote-common/           # 公共库
├── anynote-api/              # Feign 接口定义
├── anynote-modules/          # 业务微服务
│   ├── anynote-modules-system/
│   ├── anynote-modules-note/
│   ├── anynote-modules-file/
│   ├── anynote-modules-ai/
│   ├── anynote-modules-ai-nio/
│   ├── anynote-modules-manage/
│   ├── anynote-modules-notify/
│   ├── anynote-modules-job/
│   └── anynote-modules-external-api/
├── sql/                      # 数据库 schema 与初始数据
├── docker/
│   ├── mysql/init/           # MySQL 初始化脚本与 SQL
│   └── nacos/
│       ├── configs/          # Nacos 导入的配置文件
│       └── nacos-init.sh     # 配置导入脚本
├── docker-compose-middleware.yaml  # 中间件编排
├── docker-compose.yaml             # 全量编排（含应用服务）
├── Dockerfile                      # 多阶段构建（含 Maven 编译）
├── Dockerfile.local                # 快速构建（需预编译 JAR）
└── .env.example                    # 环境变量示例
```
