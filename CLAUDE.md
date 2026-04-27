# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Build all modules (skip tests when middleware is unavailable)
./mvnw clean package -DskipTests

# Run a specific service locally
./mvnw -pl anynote-modules/anynote-modules-note -am spring-boot:run

# Run all tests
./mvnw test

# Run tests for a single module and its dependencies
./mvnw -pl anynote-modules/anynote-modules-note -am test
```

Production startup uses `startup.sh` which wraps the JAR with SkyWalking agent:
```bash
java -javaagent:/path/to/skywalking-agent.jar -Xms64M -Xmx256M -jar <service>.jar
```

## Required Middleware

All services depend on Nacos for configuration and service discovery. Tests will fail without:
- **Nacos** `localhost:8848` — config + discovery (namespace: `0587fa28-1301-43db-a7a1-599c00fc3f70`)
- **MySQL** — schema in `sql/` directory (run `anynote.sql`, then remaining `sql/*.sql` files)
- **Redis** — caching
- **Elasticsearch** — full-text search
- **RocketMQ** — async messaging
- **MinIO** or **Huawei OBS** — file storage
- **XXL-Job Admin** — scheduled tasks

## Architecture

Multi-module Maven microservices project. Top-level aggregators:

| Aggregator | Purpose |
|---|---|
| `anynote-common/` | 10 shared libraries: core, security, redis, elasticsearch, rocketmq, datascope, green, canal, swagger, ai-fastapi |
| `anynote-api/` | Feign client contracts: system, note, file, ai, notify |
| `anynote-auth/` | JWT token service (port 8083) |
| `anynote-gateway/` | Spring Cloud Gateway, auth enforcement (port 8080) |
| `anynote-admin/` | Spring Boot Admin monitoring |
| `anynote-modules/` | 9 business services: system, note, file, ai, ai-nio, manage, notify, job, external-api |

**Request flow**: Client → Gateway (8080) → Auth validation → Feign → Business module

**Inter-service calls** use OpenFeign; clients live in `anynote-api/<domain>`. To call another service, inject the Feign client from the corresponding `anynote-api-*` module.

**Data scoping / permissions** are handled by `anynote-common-datascope`. Authorization rules are seeded via `sql/sys_permission_rule.sql`.

## Key Patterns

**Package layout** (consistent across all modules):
```
com.anynote.<domain>/
  controller/   — @RestController endpoints
  service/impl/ — business logic
  mapper/       — MyBatis Plus mappers
  entity/po/    — DB entities (*PO)
  entity/dto/   — API input
  entity/vo/    — API response
  feign/        — outbound Feign clients
```

**Tech stack**: Spring Boot 2.7.7 · Spring Cloud 2021.0.5 · Spring Cloud Alibaba 2021.0.4.0 · MyBatis Plus 3.5.2 · Druid connection pool · FastJSON2 · JWT (Auth0 4.4.0) · OpenAI Java 0.31.0 · Apache POI · Canal 1.1.4

**Configuration**: All runtime config is externalized to Nacos. `bootstrap.yml` in each service points to Nacos; shared config is loaded as `application-${profile}.yml`. Do not hardcode connection strings — add them to the Nacos config instead.

**Commit style**: `feat:`, `fix:`, or `update:` prefix followed by a short description (Chinese or English). Example: `feat: 添加用户关联权限`.
