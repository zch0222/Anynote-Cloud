# Repository Guidelines

## Project Structure & Module Organization
This is a multi-module Maven Spring Boot/Spring Cloud project. Root `pom.xml` aggregates these modules:

- `anynote-common/`: shared core utilities, security, Redis, RocketMQ, Elasticsearch, Swagger, data-scope, and integration helpers.
- `anynote-api/`: Feign/API contracts grouped by domain, such as note, file, AI, notify, and system.
- `anynote-auth/`, `anynote-gateway/`, `anynote-admin/`: standalone services.
- `anynote-modules/`: business services including system, note, file, manage, AI, notify, job, and external API modules.
- `sql/`: schema and seed/config SQL. `config/`: supporting JSON configuration.

Java sources live under `src/main/java`, resources under `src/main/resources`, and tests under `src/test/java`.

## Build, Test, and Development Commands
- `./mvnw clean package`: build all modules and package service jars.
- `./mvnw clean package -DskipTests`: build faster when tests require unavailable middleware.
- `./mvnw test`: run all Maven tests.
- `./mvnw -pl anynote-modules/anynote-modules-file -am test`: run one module and required dependencies.
- `./mvnw -pl anynote-auth -am spring-boot:run`: start a single service locally.

Local services use Spring Cloud/Nacos; check each `bootstrap.yml` before running. `startup.sh` is for jar startup with SkyWalking and assumes packaged jars plus a logs directory.

## Coding Style & Naming Conventions
Use Java 8, UTF-8, and 4-space indentation. Write all code comments in Chinese. Follow package roots such as `com.anynote.auth`, `com.anynote.gateway`, and `com.anynote.<domain>`. Keep suffixes consistent: `Controller`, `Service`, `ServiceImpl`, `Mapper`, `DTO`, `VO`, `BO`, and `PO`. Prefer shared helpers from `anynote-common`. No repository-wide formatter is configured, so match nearby style and keep imports organized.

## Testing Guidelines
Tests use JUnit 5 via `spring-boot-starter-test` in service modules. Place tests in the matching module under `src/test/java`, and name classes `*Test`. Many current tests are Spring Boot integration tests and may require Nacos, Redis, databases, MinIO, Elasticsearch, or RocketMQ; document required services in the test or PR. Run the narrowest module test first, then `./mvnw test` for shared behavior changes.

## Commit & Pull Request Guidelines
Recent commits use prefixes such as `feat:`, `fix:`, and `update:` followed by a concise Chinese or English summary, for example `fix: CanalMessage Gson日期`. Keep commits scoped to one behavior change.

Pull requests should include the affected module, behavior summary, linked issue when applicable, test commands run, and any SQL/configuration changes. Add screenshots or API examples only when the change affects visible UI or external contracts.

## Security & Configuration Tips
Do not commit secrets or environment-specific credentials. Keep Nacos namespaces, middleware endpoints, and storage credentials in `bootstrap.yml`, shared config, or deployment configuration.
