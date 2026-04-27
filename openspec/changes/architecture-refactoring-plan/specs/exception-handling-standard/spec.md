## ADDED Requirements

### Requirement: 禁止使用 printStackTrace 和 System.out

所有 Java 代码中必须禁止使用 `Throwable.printStackTrace()` 和 `System.out.println()`/`System.err.println()`。异常必须通过 SLF4J `log.error("描述", e)` 记录（含完整堆栈），普通输出必须使用对应日志级别方法。

#### Scenario: 异常捕获后正确记录日志

- **WHEN** catch 块捕获到异常 `e`
- **THEN** 代码中不存在 `e.printStackTrace()`，而是使用 `log.error("操作描述", e)` 记录，日志系统输出完整堆栈信息

#### Scenario: 启动日志使用日志框架输出

- **WHEN** 应用启动完成（`main` 方法或 `ApplicationRunner`）
- **THEN** 启动成功信息通过 `log.info()` 输出，代码中不存在 `System.out.println`

#### Scenario: 编译期校验（可选）

- **WHEN** 执行 `./mvnw checkstyle:check`（若引入 Checkstyle）
- **THEN** 包含 `System.out` 或 `printStackTrace` 的文件导致构建失败

---

### Requirement: 全局异常处理器覆盖参数校验异常

WebMvc 全局异常处理器（`GlobalExceptionHandler`）必须处理 `MethodArgumentNotValidException` 和 `ConstraintViolationException`，返回统一的 `ResData` 错误响应，HTTP 状态码为 400。

#### Scenario: Bean Validation 失败返回统一格式

- **WHEN** 请求参数不满足 `@NotNull`、`@NotBlank` 等约束条件
- **THEN** 响应体为 `{"code": 400, "msg": "字段校验失败信息", "data": null}`，HTTP 状态码 400，不抛出 500

#### Scenario: Controller 不包含手动参数校验逻辑

- **WHEN** Controller 方法接收 DTO 参数
- **THEN** 方法体中不存在 `if (StringUtils.isNull(...)) throw new UserParamException(...)` 模式的手动校验代码，校验由 `@Valid` + 注解驱动

---

### Requirement: Controller 接口必须有完整实现

所有已注册路由的 Controller 方法必须有实际实现，禁止直接 `return null`。

#### Scenario: 接口返回合法响应

- **WHEN** 客户端调用任意已声明的 REST 接口
- **THEN** 响应体为合法的 `ResData<T>` 对象，不返回 HTTP 200 + `null` body

#### Scenario: 未实现接口返回 501

- **WHEN** 某接口确实尚未实现（开发中）
- **THEN** 方法体抛出明确的未实现异常（如 `throw new UnsupportedOperationException("待实现")`），全局异常处理器将其转换为 HTTP 501 响应
