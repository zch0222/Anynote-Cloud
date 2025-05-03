-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: anynote_config
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` VALUES (1,'anynote-gateway-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"         \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-ai-nio\n          uri: lb://anynote-ai-nio\n          predicates:\n            - Path=/api/aiNio/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-notify\n          uri: lb://anynote-notify\n          predicates:\n            - Path=/api/notify/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-ai\n          uri: lb://anynote-ai\n          predicates:\n            - Path=/api/ai/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-note\n          uri: lb://anynote-note\n          predicates:\n            - Path=/api/note/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-manage\n          uri: lb://anynote-manage\n          predicates:\n            - Path=/api/manage/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-file\n          uri: lb://anynote-file\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 安全配置\nsecurity:\n  # 超级管理员过滤\n  manage:\n    urls:\n      - /api/manage/**\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /api/auth/register\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/note/docs/home\n      - /api/note/docs/public/*\n      - /api/ai/rag//public/**\n      # - /api/system/*','1041428d99d04cf3c83466e55acaf080','2023-07-26 03:10:00','2025-05-03 13:50:33',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(9,'anynote-system-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','2e2080cc0333b1719d19ae6decc69cc5','2023-07-27 08:56:01','2024-03-27 13:49:42',NULL,'154.7.179.45','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(24,'application-dev.yml','DEFAULT_GROUP','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      note-whisper-task-group: note_whisper_task_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      ai-chat-whisper-task-group: ai_chat_whisper_task_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n      file-whisper-task-group: file_whisper_task_group\n      \n      # canal 主题\n      canal-topic: canal_topic\n      canal-mooc-group: canal_mooc_group\n      canal-system-config-group: canal_system_config_group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nmanagement:\n  endpoints:\n    web:\n      base-path: /actuator #配置端点访问前缀\n      exposure:\n        include: \'*\'  #暴露所有端点','ec6dfa72a016952f4b08b81ca650b3a0','2023-07-28 09:20:40','2025-04-02 13:23:20',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(29,'anynote-auth-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','8bd9dada9a94822feeab40de55efced6','2023-09-25 13:12:56','2023-09-25 13:41:23',NULL,'116.148.33.74','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(37,'anynote-gateway-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','911ad69f8c162cd8db0f25d8a09beb19','2023-09-26 07:16:47','2023-09-27 03:53:42','','192.168.186.1','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','','','','yaml','',''),(38,'anynote-system-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','e1eee1408cdd40d139cf3dd7b147a380','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(39,'application-dev.yml','DEFAULT_GROUP','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','d70a09aa7a6501589265f206042fb0b3','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(40,'anynote-auth-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','8bd9dada9a94822feeab40de55efced6','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(46,'anynote-note-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:','e58b01173e0cff3be3cb789ab35a2666','2023-09-27 05:22:42','2025-03-29 06:29:08',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(51,'anynote-file-dev.yml','DEFAULT_GROUP','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','a98b3a1661b9819c9da328d59c2df8d8','2023-10-02 07:56:58','2025-03-29 06:17:37',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(65,'anynote-manage-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:','e58b01173e0cff3be3cb789ab35a2666','2023-11-11 11:16:06','2025-03-30 09:57:19',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(87,'anynote-ai-dev.yml','DEFAULT_GROUP','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','a98b3a1661b9819c9da328d59c2df8d8','2024-04-10 10:58:59','2024-06-04 12:12:15',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(90,'anynote-admin-dev.yml','DEFAULT_GROUP','management:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'','1881379326fe3835a39dc4848550ef40','2024-05-18 18:02:05','2024-05-18 18:02:05',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70',NULL,NULL,NULL,'yaml',NULL,''),(98,'anynote-ai-nio-dev.yml','DEFAULT_GROUP','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nexecutor:\n  whisper-executor:\n    core-pool-size: 16\n    max-pool-size: 200\n    queue-capacity: 1000\n  ffmpeg-executor:\n    core-pool-size: 10\n    max-pool-size: 10\n    queue-capacity: 1000\n  ','664f97d3ea21ecbe2e6714ffdb2a0868','2024-05-31 14:43:15','2025-03-15 13:00:29',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(103,'anynote-notify-dev.yml','DEFAULT_GROUP','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','c81c6318ad9d4ceabecae6162a9632db','2024-06-07 08:34:44','2024-06-09 11:57:42',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(119,'application-rocketmq-dev.yml','DEFAULT_GROUP','rocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','fcc416e2656272888a4574e4576ff8a6','2025-01-02 08:14:49','2025-03-29 06:23:44',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(125,'anynote-job-dev.yml','DEFAULT_GROUP','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:10086/xxl-job-admin\n      accessToken: default_token\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','7bc58a7d3cac6941276dfbe9e0b11f67','2025-03-15 06:54:49','2025-04-15 05:00:11',NULL,'0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','','');
/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (24,130,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml\n','e3e9ecc18058866a3c900cb86d6451f9','2024-07-28 16:18:24','2024-07-28 08:18:24',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,131,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n','d15faf7ba7841a3be2c30e8cd0c916a0','2024-07-29 01:31:12','2024-07-28 17:31:12',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,132,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: 127.0.0.1:9876\n  producer:\n    group: note-group\n\n','a7fd651e1e20185304d038967d86d8c7','2025-01-02 16:10:38','2025-01-02 08:10:39',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,133,'application-rocketmq-dev.yml','DEFAULT_GROUP','','rocketmq:\n  name-server: 192.168.100.160:9876\n  producer:\n    group: note-group','74c1d38877a314a0fe2bca0d2df43672','2025-01-02 16:14:48','2025-01-02 08:14:49',NULL,'0:0:0:0:0:0:0:1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,134,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: 192.168.100.160:9876\n  producer:\n    group: note-group\n\n','e7eae43f7d81f182a4c237bbe6d84f59','2025-01-02 16:16:25','2025-01-02 08:16:25',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,135,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','2e2080cc0333b1719d19ae6decc69cc5','2025-01-02 16:18:16','2025-01-02 08:18:17',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,136,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\nrocketmq:\n  name-server: 192.168.100.160:9876\n  producer:\n    group: note-group\n','bcd405466cdb62d419fe51d6ce15615d','2025-01-02 16:19:05','2025-01-02 08:19:05',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,137,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n','53e51bc5f08f7d2040f80c0cc211002a','2025-01-02 16:20:05','2025-01-02 08:20:06',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,138,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: 192.168.100.160:9876\n  producer:\n    group: note-group','93e0412cc1e781674d8f6b4650c7ecc4','2025-01-02 16:23:50','2025-01-02 08:23:51',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,139,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin\n### 调度中心通讯TOKEN [选填]：非空时启用；\nxxl.job.admin.accessToken=\n### 调度中心通讯超时时间[选填]，单位秒；默认3s；\nxxl.job.admin.timeout=3\n### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\nxxl.job.executor.appname=anynote-job\n### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\nxxl.job.executor.address=\n### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\nxxl.job.executor.ip=\n### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\nxxl.job.executor.port=-1\n### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\nxxl.job.executor.logpath=/Users/zch/code/anynote/xxl-job-logs/jobhandler\n### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\nxxl.job.executor.logretentiondays=30','2b8da1e3bffec83667dfeef53197bf0e','2025-03-15 14:54:49','2025-03-15 06:54:49',NULL,'0:0:0:0:0:0:0:1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,140,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin\n### 调度中心通讯TOKEN [选填]：非空时启用；\nxxl.job.admin.accessToken=\n### 调度中心通讯超时时间[选填]，单位秒；默认3s；\nxxl.job.admin.timeout=3\n### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\nxxl.job.executor.appname=anynote-job\n### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\nxxl.job.executor.address=\n### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\nxxl.job.executor.ip=\n### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\nxxl.job.executor.port=-1\n### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\nxxl.job.executor.logpath=/Users/zch/code/anynote/xxl-job-logs/jobhandler\n### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\nxxl.job.executor.logretentiondays=30','2b8da1e3bffec83667dfeef53197bf0e','2025-03-15 14:55:08','2025-03-15 06:55:09',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,141,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin\n### 调度中心通讯TOKEN [选填]：非空时启用；\nxxl.job.admin.accessToken=\n### 调度中心通讯超时时间[选填]，单位秒；默认3s；\nxxl.job.admin.timeout=3\n### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\nxxl.job.executor.appname=anynote-job\n### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\nxxl.job.executor.address=\n### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\nxxl.job.executor.ip=\n### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\nxxl.job.executor.port=-1\n### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\nxxl.job.executor.logpath=/Users/zch/code/anynote/xxl-job-logs/jobhandler\n### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\nxxl.job.executor.logretentiondays=30','2b8da1e3bffec83667dfeef53197bf0e','2025-03-15 14:58:10','2025-03-15 06:58:11',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,142,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8080/xxl-job-admin\n      accessToken:\n      timeout: 3\n    executor:\n      appname: anynote-job\n      port: -1\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      logretentiondays: 30','377d10167a1f7e2853fb91416b697d73','2025-03-15 14:58:37','2025-03-15 06:58:38',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,143,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken:\n      timeout: 3\n    executor:\n      appname: anynote-job\n      port: -1\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      logretentiondays: 30','1eac06c9b014b50770637fe2e9b262ac','2025-03-15 15:04:36','2025-03-15 07:04:37',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,144,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken:\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','89a4ec263f0432a8ce3ef499879a94ae','2025-03-15 15:07:10','2025-03-15 07:07:10',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,145,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken: yxlm\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','b8e925f028f32e6fa469198d5b6d86a3','2025-03-15 15:07:24','2025-03-15 07:07:24',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,146,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken: yxlm\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','b8e925f028f32e6fa469198d5b6d86a3','2025-03-15 15:09:50','2025-03-15 07:09:51',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(98,147,'anynote-ai-nio-dev.yml','DEFAULT_GROUP','','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','c81c6318ad9d4ceabecae6162a9632db','2025-03-15 20:40:25','2025-03-15 12:40:26',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(98,148,'anynote-ai-nio-dev.yml','DEFAULT_GROUP','','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nexecutor:\n  whisper-executor:\n    core-pool-size: 40\n    max-pool-size: 200\n  ffmpeg-executor:\n    core-pool-size: 16\n    max-pool-size: 16\n  ','55ca923db1132708d7921470dffad897','2025-03-15 20:56:18','2025-03-15 12:56:19',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(98,149,'anynote-ai-nio-dev.yml','DEFAULT_GROUP','','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nexecutor:\n  whisper-executor:\n    core-pool-size: 40\n    max-pool-size: 200\n    queue-capacity: 1000\n  ffmpeg-executor:\n    core-pool-size: 16\n    max-pool-size: 16\n    queue-capacity: 1000\n  ','89418b0d89171b86ea2007df659c8076','2025-03-15 21:00:29','2025-03-15 13:00:29',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(51,150,'anynote-file-dev.yml','DEFAULT_GROUP','','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:','baf625643a5a92004ec94d1deec45f63','2025-03-29 14:17:36','2025-03-29 06:17:37',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,151,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','84f485b61c529ad066af30ca8f0231ce','2025-03-29 14:20:55','2025-03-29 06:20:56',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(119,152,'application-rocketmq-dev.yml','DEFAULT_GROUP','','rocketmq:\n  name-server: 192.168.100.160:9876\n  producer:\n    group: note-group','74c1d38877a314a0fe2bca0d2df43672','2025-03-29 14:23:44','2025-03-29 06:23:44',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,153,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','6c56c274c99dcec2856e5d9538618a3d','2025-03-29 14:29:08','2025-03-29 06:29:08',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,154,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','3930f7d1864d3d2caf5409362fef0f7f','2025-03-29 14:51:58','2025-03-29 06:51:58',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(65,155,'anynote-manage-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:','e58b01173e0cff3be3cb789ab35a2666','2025-03-30 17:52:59','2025-03-30 09:53:00',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(65,156,'anynote-manage-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\nmanagement:\n  endpoints:\n    web:\n      base-path: /actuator #配置端点访问前缀\n      exposure:\n        include: \'*\'  #暴露所有端点','dac9d49ab9cd392f83f69c50787a54b4','2025-03-30 17:57:19','2025-03-30 09:57:19',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,157,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      note-whisper-task-group: note_whisper_task_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      ai-chat-whisper-task-group: ai_chat_whisper_task_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n      file-whisper-task-group: file_whisper_task_group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group','b2d5a01f9428e21f8603adc0509efc6a','2025-03-30 17:57:28','2025-03-30 09:57:29',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,158,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      note-whisper-task-group: note_whisper_task_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      ai-chat-whisper-task-group: ai_chat_whisper_task_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n      file-whisper-task-group: file_whisper_task_group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nmanagement:\n  endpoints:\n    web:\n      base-path: /actuator #配置端点访问前缀\n      exposure:\n        include: \'*\'  #暴露所有端点','b0ce58c566bae07571f640c73dcc42a2','2025-03-31 21:45:18','2025-03-31 13:45:19',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,159,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      note-whisper-task-group: note_whisper_task_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      ai-chat-whisper-task-group: ai_chat_whisper_task_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n      file-whisper-task-group: file_whisper_task_group\n      \n      # canal 主题\n      canal-topic: canal_topic\n      canal-mooc-group: canal_mooc_group\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nmanagement:\n  endpoints:\n    web:\n      base-path: /actuator #配置端点访问前缀\n      exposure:\n        include: \'*\'  #暴露所有端点','733395357cd2eb29944d7f076d936628','2025-04-02 21:07:54','2025-04-02 13:07:54',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,160,'application-dev.yml','DEFAULT_GROUP','','spring:\n  http:\n    multipart:\n      enabled: false\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  servlet:\n    encoding:\n      charset: utf-8\n      force: true\n      enabled: true\n    multipart:\n      max-file-size: 500MB\n      max-request-size: 500MB\n    \n\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n  data:\n    rocketmq:\n      note-task-topic: note_task_topic\n      note-task-group: note_task_group\n      note-topic: note_topic\n      note-group: note_group\n      note-whisper-task-group: note_whisper_task_group\n      doc-topic: doc_topic\n      doc-group: doc_group\n      rag-topic: rag_topic\n      rag-group: rag_group\n      ai-chat-topic: ai_chat_topic\n      ai-chat-group: ai_chat_group\n      ai-chat-whisper-task-group: ai_chat_whisper_task_group\n      notify-topic: notify-topic\n      notify-group: notify-group\n      notify-note-group: notify-note-group\n      whisper-group: whisper-group\n      file-whisper-task-group: file_whisper_task_group\n      \n      # canal 主题\n      canal-topic: canal_topic\n      canal-mooc-group: canal_mooc_group\n      canal-system-config-gruop: canal_system_config_gruop\n  external-resources:\n    # 允许引用的资源链接\n    allowed-domains:\n      - anynote.obs.cn-east-3.myhuaweicloud.com\n  ai-fastapi:\n    address: http://localhost:8000\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath*:mapper/*.xml\n\nrocketmq:\n  name-server: localhost:9876\n  producer:\n    group: note-group\n\nmanagement:\n  endpoints:\n    web:\n      base-path: /actuator #配置端点访问前缀\n      exposure:\n        include: \'*\'  #暴露所有端点','9531de5f99f4e0c0e1fecf450df8e62f','2025-04-02 21:23:20','2025-04-02 13:23:20',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,161,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken: default_token\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','0747810b3577595493f931213ff5a8db','2025-04-12 15:13:02','2025-04-12 07:13:03',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,162,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8085/xxl-job-admin\n      accessToken: default_token\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30\n\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832','3d2222a7f1c5632207d70e48c53551b2','2025-04-12 15:18:40','2025-04-12 07:18:41',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,163,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8086/xxl-job-admin\n      accessToken: default_token\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30\n\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832','09e79323ef34a547ccd3bcd721928ea3','2025-04-12 15:22:51','2025-04-12 07:22:51',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(125,164,'anynote-job-dev.yml','DEFAULT_GROUP','','### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8086/xxl-job-admin\n      accessToken: default_token\n      timeout: 3\n    executor:\n      ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: anynote-job\n      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。\n      address:\n      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 \"执行器注册\" 和 \"调度中心请求并触发任务\"；\n      ip:\n      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；\n      port: -1\n      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；\n      logpath: /Users/zch/code/anynote/xxl-job-logs/jobhandler\n      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；\n      logretentiondays: 30','894953ef467cd1c7d8892ee2e5399e0c','2025-04-15 13:00:11','2025-04-15 05:00:11',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,165,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"         \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-ai-nio\n          uri: lb://anynote-ai-nio\n          predicates:\n            - Path=/api/aiNio/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-notify\n          uri: lb://anynote-notify\n          predicates:\n            - Path=/api/notify/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-ai\n          uri: lb://anynote-ai\n          predicates:\n            - Path=/api/ai/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-note\n          uri: lb://anynote-note\n          predicates:\n            - Path=/api/note/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-manage\n          uri: lb://anynote-manage\n          predicates:\n            - Path=/api/manage/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-file\n          uri: lb://anynote-file\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 超级管理员过滤\n  manage:\n    urls:\n      - /api/manage/**\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /api/auth/register\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/note/docs/home\n      - /api/note/docs/public/*\n      - /api/ai/rag//public/**\n      # - /api/system/*','e0c29ee6bb247a956b4b399e06f8fd9c','2025-05-03 21:50:32','2025-05-03 13:50:33',NULL,'0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('nacos','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` VALUES (1,'1','0587fa28-1301-43db-a7a1-599c00fc3f70','dev','开发环境','nacos',1690340127460,1690340127460),(2,'1','b33fac79-0b97-464e-9d1e-b01bb9a48e53','local','本地开发','nacos',1695712594567,1695712594567);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('jack','$2a$10$7SQb5t4FbAgwrdFdh51znedrWGEvXE83qH85AG5UW7SVyY4UsU55C',1),('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1),('nacos_admin','$2a$10$YRJGfMk6R/sDxtQunQcjLe3bnySrUUht/gsL2tt7SoQE3vZY0w0gG',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-03 21:50:50
