## ADDED Requirements

### Requirement: KnowledgeBaseService 仅负责核心 CRUD

`KnowledgeBaseService` 及其实现类必须只包含知识库的创建、读取、更新、删除操作，以及权限校验逻辑。成员管理、导入导出相关方法必须迁移到独立 Service。

#### Scenario: 核心 Service 不包含 Excel 处理逻辑

- **WHEN** 查看 `KnowledgeBaseServiceImpl` 源码
- **THEN** 类中不包含对 Apache POI（`XSSFWorkbook`、`HSSFWorkbook`）的任何引用，Excel 操作全部委托给 `KnowledgeBaseImportExportService`

#### Scenario: 核心 Service 不包含成员管理逻辑

- **WHEN** 查看 `KnowledgeBaseServiceImpl` 源码
- **THEN** 类中不包含知识库成员邀请、移除、角色变更的业务方法，这些方法全部在 `KnowledgeBaseUserService` 中

---

### Requirement: KnowledgeBaseUserService 负责成员管理

新建 `KnowledgeBaseUserService` 接口及 `KnowledgeBaseUserServiceImpl` 实现，必须包含知识库成员的邀请、移除、角色变更等操作，每个写操作方法独立加 `@Transactional`。

#### Scenario: 成员邀请操作事务独立

- **WHEN** 调用 `KnowledgeBaseUserService#inviteUser` 过程中发生异常
- **THEN** 仅本次邀请操作回滚，不影响知识库其他数据

#### Scenario: Controller 通过新 Service 调用成员管理

- **WHEN** 知识库成员管理相关 Controller 接口被调用
- **THEN** Controller 注入并调用 `KnowledgeBaseUserService`，不再调用 `KnowledgeBaseService` 中的成员管理方法

---

### Requirement: KnowledgeBaseImportExportService 负责导入导出

新建 `KnowledgeBaseImportExportService` 接口及实现，必须包含 Excel 导入成员、导出知识库数据等文件处理操作。该 Service 可调用 `anynote-api-file` Feign 客户端进行文件上传，但不得调用 `KnowledgeBaseService` 的写操作（避免事务嵌套）。

#### Scenario: 导入操作独立于核心事务

- **WHEN** Excel 导入过程中某行数据格式错误
- **THEN** 已成功导入的行不回滚（逐行提交），错误行记录在返回的错误列表中，不影响知识库基本信息

#### Scenario: 禁止通过 getBaseMapper 跨层访问

- **WHEN** 查看三个拆分后的 Service 实现类
- **THEN** 代码中不存在 `service.getBaseMapper().selectById(...)` 调用模式，所有数据库操作通过本 Service 或对应 Mapper 访问
