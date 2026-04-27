## Context

Anynote Cloud 是一个持续迭代中的微服务项目，当前代码库存在若干历史遗留问题：异常处理不统一（`printStackTrace`/`System.out` 混用）、WebClientConfig 等配置类在 5 个模块中重复定义、`KnowledgeBaseServiceImpl` 单文件超 600 行且职责混杂、部分 Controller 接口返回硬编码 `null`、JWT 密钥为 4 位弱密钥。本次重构以"不破坏 API 契约、不引入新框架"为约束，逐步消除以上问题。

## Goals / Non-Goals

**Goals:**
- 统一全项目异常日志记录方式，消除 `printStackTrace` 和 `System.out`
- 将重复的 `WebClientConfig` 收拢到 `anynote-common-core`，各模块通过依赖复用
- 拆分 `KnowledgeBaseServiceImpl` 为职责单一的三个 Service，明确事务边界
- 修复未完成的 Controller 方法（返回 `null` 的接口）
- 加固 JWT 密钥配置，改为强密钥 + 环境变量注入
- 清理 Controller 中的手动参数校验，替换为 Bean Validation

**Non-Goals:**
- 不重构模块间 Feign 调用链路（依赖关系整理留待后续）
- 不引入新的日志框架或 APM 组件
- 不修改数据库 schema 或 API 接口签名
- 不处理 `anynote-modules-external-api` 的业务填充（仅做最小清理）

## Decisions

### 决策 1：WebClientConfig 提取位置选 `anynote-common-core`

**选择**：在 `anynote-common-core` 中新建 `WebClientConfig`，各业务模块删除本地副本，通过已有的 `anynote-common-core` 依赖自动继承。

**理由**：`anynote-common-core` 已被所有业务模块依赖，无需新增 pom 依赖。提取后各模块的 `config/WebClientConfig.java` 直接删除即可，零侵入。

**备选方案**：新建 `anynote-common-webclient` 子模块——引入额外模块开销，对于单一 Bean 的提取过度设计。

---

### 决策 2：KnowledgeBaseServiceImpl 拆分策略

**选择**：按职责拆分为三个 Service：
- `KnowledgeBaseService`：核心 CRUD、查询、权限校验
- `KnowledgeBaseUserService`：成员管理（邀请、移除、角色变更）
- `KnowledgeBaseImportExportService`：Excel 导入导出、文件处理

**事务边界**：每个 Service 的写操作方法独立加 `@Transactional`，不跨 Service 持有事务。

**理由**：三类操作的依赖和外部调用完全不同（导入依赖文件上传 Feign、成员管理依赖 System 模块），拆分后可独立测试、独立演进。

**备选方案**：保持单一 Service 只拆 Impl——职责混杂问题依然存在，测试仍困难。

---

### 决策 3：异常日志统一方式

**选择**：将所有 `catch` 块中的 `e.printStackTrace()` 替换为 `log.error("描述", e)`（SLF4J，自动输出堆栈）；所有 `System.out.println` 替换为对应级别的 `log.info/warn`。全局异常处理器不做结构调整（WebMvc 和 WebFlux 各保留一个），仅补充缺失的异常类型处理。

**理由**：不引入新框架，最小化变更范围，修改完全向后兼容。

---

### 决策 4：JWT 密钥加固方式

**选择**：在 Nacos 的 `application-dev.yml` 中将 `secret: yxlm` 替换为占位符 `secret: ${ANYNOTE_JWT_SECRET}`，并在 Docker Compose 的 `.env.example` 和部署文档中说明需设置该环境变量（最小长度 32 位）。

**理由**：密钥不应出现在任何提交的文件中；通过环境变量注入符合 12-Factor 原则，与现有配置模式（数据库密码已采用此模式）保持一致。

---

### 决策 5：Controller 参数校验重构

**选择**：移除 `NoteController` 等处的手动 `if (StringUtils.isNull(...)) throw` 逻辑，在 DTO/参数类字段上补充 `@NotNull`、`@NotBlank` 等注解，依赖已有的全局异常处理器捕获 `MethodArgumentNotValidException`。

**理由**：全局异常处理器（`GlobalExceptionHandler`）已处理 `MethodArgumentNotValidException`，无需额外工作，只需在 Controller 方法参数加 `@Valid`。

## Risks / Trade-offs

| 风险 | 缓解措施 |
|---|---|
| KnowledgeBaseServiceImpl 拆分后，原有调用方（Controller、Feign）需同步更新注入点 | 拆分前全局搜索注入点，统一在同一 PR 中修改，确保编译通过后再合并 |
| WebClientConfig 提取后，各模块若有定制化 Bean 配置会被覆盖 | 提取前逐一对比 5 份配置内容，对差异项保留本地 Bean 并加 `@ConditionalOnMissingBean` |
| JWT 密钥变更后，存量 Token 全部失效（需重新登录） | 在非业务低峰期部署，提前通知用户；dev 环境无此顾虑 |
| 修复返回 `null` 的接口可能影响前端联调 | 与前端确认这些接口当前是否已接入；若未接入则直接实现，若已接入则需协调 |

## Migration Plan

1. 按任务分组逐批提交，每批独立可验证（`./mvnw clean package` 通过）。
2. JWT 密钥变更：先在 Nacos 更新配置，再重启 `anynote-auth` 服务，其他服务无需重启。
3. **回滚**：每个任务组对应独立 commit，回滚时 `git revert` 对应 commit 即可，无 schema 变更无需数据迁移。

## Open Questions

- `anynote-modules-external-api` 是否有计划中的业务功能？若有，本次仅清理 `System.out`；若无计划，是否直接从父 pom 注释掉该模块？
- `VideoController#videoUploadTempLink` 返回 `null`，是有意留空还是遗漏实现？需与业务方确认后再处理。
