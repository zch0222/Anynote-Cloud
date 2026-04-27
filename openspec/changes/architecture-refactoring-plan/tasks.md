## 1. 异常处理与日志规范化

- [ ] 1.1 全局搜索 `e.printStackTrace()`，逐一替换为 `log.error("描述", e)`（涉及文件：`NoteElasticsearchServiceImpl`、`KnowledgeBaseServiceImpl` 等）
- [ ] 1.2 全局搜索 `System.out.println` 和 `System.err.println`，替换为 `log.info/warn/error`（涉及所有 `*Application.java` 启动类）
- [ ] 1.3 在 `GlobalExceptionHandler`（WebMvc）中补充 `MethodArgumentNotValidException` 和 `ConstraintViolationException` 的处理方法，返回统一 `ResData` 400 响应
- [ ] 1.4 在 `GlobalExceptionHandler`（WebMvc）中补充 `UnsupportedOperationException` 处理，返回 501 响应
- [ ] 1.5 验证：执行 `./mvnw clean package -DskipTests`，确认编译通过

## 2. Controller 参数校验重构

- [ ] 2.1 在 `NoteController#getNotesByKnowledgeBaseId` 中移除手动 `if (isNull(page))` 校验，改为在 `NoteQueryParam` 对应字段加 `@NotNull` 注解，方法参数加 `@Valid`
- [ ] 2.2 全局搜索其他 Controller 中类似的手动参数校验模式，统一改为 Bean Validation
- [ ] 2.3 修复 `NoteController#getNoteInfoList`：实现实际查询逻辑或抛出 `UnsupportedOperationException("待实现")`，消除 `return null`
- [ ] 2.4 修复 `VideoController#videoUploadTempLink`：实现实际逻辑或抛出 `UnsupportedOperationException("待实现")`，消除 `return null`
- [ ] 2.5 验证：启动 `anynote-modules-note`，调用相关接口，确认参数缺失时返回 400 而非 500

## 3. 共享 WebClientConfig 提取

- [ ] 3.1 对比 5 个模块（note、file、ai、ai-nio、manage）中的 `WebClientConfig`/`WebclientConfig` 内容，记录差异项
- [ ] 3.2 在 `anynote-common-core` 的 `com.anynote.core.config` 包下新建 `WebClientConfig`，注册 `WebClient.Builder` Bean，加 `@ConditionalOnMissingBean`
- [ ] 3.3 删除 `anynote-modules-note`、`anynote-modules-file`、`anynote-modules-ai`、`anynote-modules-ai-nio`、`anynote-modules-manage` 中的本地 `WebClientConfig`（无差异项的）
- [ ] 3.4 对有差异的模块，在保留本地 Bean 的同时确认不与公共 Bean 冲突（`@ConditionalOnMissingBean` 已保障）
- [ ] 3.5 验证：对各涉及模块执行 `./mvnw -pl anynote-modules/anynote-modules-note -am clean package -DskipTests`，确认编译通过

## 4. JWT 密钥加固

- [ ] 4.1 在 `docker/nacos/configs/application-dev.yml`（或 Nacos 中对应配置）将 `secret: yxlm` 改为 `secret: ${ANYNOTE_JWT_SECRET}`
- [ ] 4.2 在 `.env.example` 中添加 `ANYNOTE_JWT_SECRET=` 条目，注释说明要求长度 ≥ 32 位随机字符串
- [ ] 4.3 在 `docker-compose-middleware.yaml` 的 nacos-init 配置或文档中说明需提前设置该变量
- [ ] 4.4 验证：本地设置 `ANYNOTE_JWT_SECRET=test-secret-32-characters-minimum`，启动 `anynote-auth`，确认 Token 签发正常

## 5. KnowledgeBaseService 拆分

- [ ] 5.1 在 `anynote-modules-note` 中新建 `KnowledgeBaseUserService` 接口（`com.anynote.note.service`），声明成员邀请、移除、角色变更方法
- [ ] 5.2 新建 `KnowledgeBaseUserServiceImpl`，从 `KnowledgeBaseServiceImpl` 中迁移成员管理相关方法，各写操作方法加 `@Transactional`
- [ ] 5.3 新建 `KnowledgeBaseImportExportService` 接口，声明 Excel 导入导出方法
- [ ] 5.4 新建 `KnowledgeBaseImportExportServiceImpl`，从 `KnowledgeBaseServiceImpl` 中迁移 Excel/文件处理相关方法
- [ ] 5.5 更新 `KnowledgeBaseServiceImpl`：移除已迁移的方法，保留核心 CRUD 和权限校验
- [ ] 5.6 更新 Controller 中的注入点：将成员管理接口改为注入 `KnowledgeBaseUserService`，导入导出接口改为注入 `KnowledgeBaseImportExportService`
- [ ] 5.7 移除代码中 `service.getBaseMapper().selectById(...)` 调用模式，在对应 Service 接口中添加包装方法
- [ ] 5.8 验证：执行 `./mvnw -pl anynote-modules/anynote-modules-note -am clean package -DskipTests`，确认编译通过

## 6. 废弃代码清理

- [ ] 6.1 确认 `anynote-modules-external-api` 无在用业务逻辑后，移除其 `main` 方法中的 `System.out.println`，并在父 pom 中添加注释说明该模块状态
- [ ] 6.2 全局搜索并移除其他模块中重复的工具类（与 `anynote-common-core` 中已有实现相同的），替换调用方引用

## 7. 回归验证

- [ ] 7.1 执行 `./mvnw clean package -DskipTests`，确认全量构建通过
- [ ] 7.2 执行 `./mvnw test`（需 Nacos、Redis、MySQL 可用），确认现有测试无回归
- [ ] 7.3 代码审查：确认无 `printStackTrace`、`System.out`、`return null` 遗留（可用 `grep -r "printStackTrace\|System\.out" --include="*.java" .` 验证）
