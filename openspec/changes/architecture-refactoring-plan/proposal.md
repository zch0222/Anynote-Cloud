## Why

随着 Anynote Cloud 功能持续扩展，代码库中积累了多类架构问题：模块间耦合过重、Service 层职责过于集中、异常处理不统一、重复配置类分散在各模块、部分接口未完成实现。这些问题已影响到可维护性、可测试性和系统稳定性，需要在继续迭代新功能前完成针对性重构。

## What Changes

**代码质量修复（无 Breaking Change）**
- 移除所有 `e.printStackTrace()` 调用，统一使用 `log.error()` 记录异常堆栈
- 移除所有 `System.out.println()` 调用，替换为 SLF4J 日志
- 修复返回 `null` 的未完成 Controller 方法（`NoteController#getNoteInfoList`、`VideoController#videoUploadTempLink` 等）
- 移除 Controller 中的手动参数校验代码，改用 Bean Validation 注解

**代码复用优化**
- 将分散在 5 个模块中的 `WebClientConfig` 配置类提取到 `anynote-common-core`
- 梳理并移除各模块中重复的工具类和配置类

**Service 层拆分**
- 拆分 `KnowledgeBaseServiceImpl`（600+ 行）为 `KnowledgeBaseService`、`KnowledgeBaseUserService`、`KnowledgeBaseImportExportService`
- 从 Service 实现中移除 `getBaseMapper()` 直接调用，改为在 Service 接口中提供业务方法包装

**模块依赖整理**
- 梳理 `anynote-modules-file`、`anynote-modules-note` 的 pom.xml，移除未实际使用的 API 依赖

**安全配置加固**
- 将硬编码的 JWT secret（当前为 `yxlm`，4 位）替换为强密钥，并通过环境变量注入
- 移除配置文件中暴露的数据库默认密码明文

**清理废弃代码**
- 处置 `anynote-modules-external-api`：填充最小实现或在 pom.xml 中注释掉该模块

## Capabilities

### New Capabilities

- `exception-handling-standard`：统一异常处理规范，覆盖日志记录方式、全局异常处理器职责划分（WebMvc vs WebFlux）
- `shared-webclient-config`：将 WebClient 配置提取到公共模块，消除各业务模块的重复配置
- `knowledge-base-service-split`：将 KnowledgeBaseServiceImpl 按职责拆分为多个 Service，明确事务边界

### Modified Capabilities

（无现有 spec 的行为级变更）

## Impact

- **直接影响模块**：`anynote-modules-note`、`anynote-modules-file`、`anynote-modules-ai`、`anynote-modules-ai-nio`、`anynote-modules-manage`、`anynote-common-core`
- **配置影响**：JWT secret 由硬编码改为环境变量，部署时需在 Nacos 或容器环境变量中补充 `ANYNOTE_JWT_SECRET`
- **API 契约**：无 Breaking Change，所有修改为内部实现变更
- **SQL 影响**：无
- **测试**：受影响模块需在重构后补充或更新单元测试
