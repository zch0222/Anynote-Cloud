## ADDED Requirements

### Requirement: WebClient 配置在公共模块统一定义

`anynote-common-core` 必须提供一个 `WebClientConfig` Bean，注册默认的 `WebClient.Builder`。该 Bean 必须使用 `@ConditionalOnMissingBean` 修饰，允许业务模块在有差异化需求时覆盖。

#### Scenario: 业务模块复用公共 WebClient Bean

- **WHEN** 业务模块（如 `anynote-modules-note`）已删除本地 `WebClientConfig`，并依赖 `anynote-common-core`
- **THEN** 应用上下文中存在且仅存在一个 `WebClient.Builder` Bean，注入正常，无 `NoSuchBeanDefinitionException`

#### Scenario: 业务模块可覆盖公共 Bean

- **WHEN** 某模块需要定制化 `WebClient`（如自定义超时、BaseURL）并在本地定义了 `WebClientConfig`
- **THEN** `@ConditionalOnMissingBean` 使公共 Bean 不生效，模块本地 Bean 优先注册，无冲突

---

### Requirement: 各业务模块不重复定义 WebClientConfig

`anynote-modules-note`、`anynote-modules-file`、`anynote-modules-ai`、`anynote-modules-ai-nio`、`anynote-modules-manage` 中的本地 `WebClientConfig`（或 `WebclientConfig`）必须被删除，除非该模块有与公共配置不同的定制需求。

#### Scenario: 删除重复配置后编译通过

- **WHEN** 删除上述模块中的本地 `WebClientConfig` 类后执行 `./mvnw clean package -DskipTests`
- **THEN** 构建成功，无编译错误

#### Scenario: 注入 WebClient 的 Service 正常工作

- **WHEN** 删除本地 `WebClientConfig` 后启动对应服务
- **THEN** 注入了 `WebClient` 或 `WebClient.Builder` 的 Service 正常初始化，无 Bean 注入异常

---

### Requirement: JWT 密钥通过环境变量注入

`anynote-auth` 及所有依赖 JWT 校验的模块，JWT secret 必须通过环境变量 `ANYNOTE_JWT_SECRET` 注入，配置文件中不得包含明文密钥值。生产环境密钥长度必须不少于 32 位。

#### Scenario: 环境变量缺失时服务拒绝启动

- **WHEN** 未设置 `ANYNOTE_JWT_SECRET` 环境变量且配置文件中无默认值
- **THEN** 应用启动失败并输出明确错误信息，提示缺少必要配置

#### Scenario: 设置强密钥后 Token 签发正常

- **WHEN** `ANYNOTE_JWT_SECRET` 设置为 32 位以上随机字符串
- **THEN** `anynote-auth` 正常签发 JWT，其他服务校验 Token 成功
