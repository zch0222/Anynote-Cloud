-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: anynote_config
-- ------------------------------------------------------
-- Server version	8.0.32

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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` VALUES (1,'anynote-gateway-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"         \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\n        - id: anynote-note\n          uri: lb://anynote-note\n          predicates:\n            - Path=/api/note/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','2886f0be9064ea1392d36bbf64725aba','2023-07-26 03:10:00','2023-09-27 09:43:24','nacos','0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(9,'anynote-system-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: 123456\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','ba99dda7231c1c408d2e8b5b708c511a','2023-07-27 08:56:01','2023-09-30 14:12:04','','192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(24,'application-dev.yml','DEFAULT_GROUP','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 10080\n    secret: yxlm\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','e0996cba9439a0a393838492f1dace38','2023-07-28 09:20:40','2023-09-27 11:21:07','nacos','0:0:0:0:0:0:0:1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(29,'anynote-auth-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','8bd9dada9a94822feeab40de55efced6','2023-09-25 13:12:56','2023-09-25 13:41:23',NULL,'116.148.33.74','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(37,'anynote-gateway-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','911ad69f8c162cd8db0f25d8a09beb19','2023-09-26 07:16:47','2023-09-27 03:53:42','','192.168.186.1','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','','','','yaml','',''),(38,'anynote-system-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','e1eee1408cdd40d139cf3dd7b147a380','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(39,'application-dev.yml','DEFAULT_GROUP','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','d70a09aa7a6501589265f206042fb0b3','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(40,'anynote-auth-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','8bd9dada9a94822feeab40de55efced6','2023-09-26 07:16:47','2023-09-26 07:16:47',NULL,'116.148.33.74','','b33fac79-0b97-464e-9d1e-b01bb9a48e53','',NULL,NULL,'yaml',NULL,''),(46,'anynote-note-dev.yml','DEFAULT_GROUP','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: 123456\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','ba99dda7231c1c408d2e8b5b708c511a','2023-09-27 05:22:42','2023-09-30 14:12:42','','192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(51,'anynote-file-dev.yml','DEFAULT_GROUP','  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:','244781e698147466e31aff2e91430ba1','2023-10-02 07:56:58','2023-10-02 07:56:58',NULL,'192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70',NULL,NULL,NULL,'yaml',NULL,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (24,29,'application-dev.yml','DEFAULT_GROUP','','spring:\n#   autoconfigure:\n#     exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm','8d8f8ef5b4ea53953efb6fa602b4ac7e','2023-09-25 21:09:36','2023-09-25 13:09:37',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(9,30,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832','4aa148701252a458544dae266d447d70','2023-09-25 21:09:49','2023-09-25 13:09:49',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,31,'anynote-auth-dev.yml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:','9fcbf3486b77a337b0c1b42eb0e0f9b8','2023-09-25 21:12:56','2023-09-25 13:12:56',NULL,'116.148.33.74','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,32,'application-dev.yml','DEFAULT_GROUP','','spring:\n#   autoconfigure:\n#     exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm','be4503b4dbc98515681b4d57dc94e21b','2023-09-25 21:25:52','2023-09-25 13:25:52',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(29,33,'anynote-auth-dev.yml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:','9fcbf3486b77a337b0c1b42eb0e0f9b8','2023-09-25 21:28:28','2023-09-25 13:28:29',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(29,34,'anynote-auth-dev.yml','DEFAULT_GROUP','','spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','adb731ebd0e4c845bcce9cb31075e62f','2023-09-25 21:41:22','2023-09-25 13:41:23',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(9,35,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','003751f7f3af149ea438e9d992e3623d','2023-09-25 21:41:33','2023-09-25 13:41:34',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,36,'application-dev.yml','DEFAULT_GROUP','','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm','49e08d924b2c35613619f7e54634f89d','2023-09-25 21:41:43','2023-09-25 13:41:43',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(9,37,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n\n','eafbc6978b4afb6f2c95deaaa8b12faf','2023-09-25 21:42:24','2023-09-25 13:42:24',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,38,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','561b71f3c1f1e511c7064a548b10602d','2023-09-26 13:50:45','2023-09-26 05:50:45',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,39,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','28b7a3b5e9770132ea79c1ae37bcf9ac','2023-09-26 15:16:46','2023-09-26 07:16:47',NULL,'116.148.33.74','I','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(0,40,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','e1eee1408cdd40d139cf3dd7b147a380','2023-09-26 15:16:46','2023-09-26 07:16:47',NULL,'116.148.33.74','I','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(0,41,'application-dev.yml','DEFAULT_GROUP','','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','d70a09aa7a6501589265f206042fb0b3','2023-09-26 15:16:46','2023-09-26 07:16:47',NULL,'116.148.33.74','I','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(0,42,'anynote-auth-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n','8bd9dada9a94822feeab40de55efced6','2023-09-26 15:16:46','2023-09-26 07:16:47',NULL,'116.148.33.74','I','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(1,43,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','28b7a3b5e9770132ea79c1ae37bcf9ac','2023-09-27 11:42:57','2023-09-27 03:42:58',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(37,44,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','28b7a3b5e9770132ea79c1ae37bcf9ac','2023-09-27 11:43:27','2023-09-27 03:43:25','','192.168.186.1','U','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(37,45,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowedMethods: \"*\"    \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','2df53b1c6823eb73b29e2364c48cd537','2023-09-27 11:53:48','2023-09-27 03:53:42','','192.168.186.1','U','b33fac79-0b97-464e-9d1e-b01bb9a48e53',''),(1,46,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        cors-configurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedHeaders: \"*\"\n            allowedMethods: \"*\"             \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','d78e59e8842b93b4540df1aba2e96144','2023-09-27 11:55:30','2023-09-27 03:55:26','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,47,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"         \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','37ab2b7c2cd2b21ac12967d6d5767fd6','2023-09-27 11:57:00','2023-09-27 03:57:01',NULL,'116.148.33.74','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,48,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: anynote\r\n    password: Anynote*1832\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n\r\n','a69b9fd19d288c8c9b7ebbf7e7cc9c67','2023-09-27 13:22:42','2023-09-27 05:22:42',NULL,'0:0:0:0:0:0:0:1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,49,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOriginPatterns: \"*\"\n            allowed-methods: \"*\"\n            allowed-headers: \"*\"\n            allow-credentials: true\n            exposedHeaders: \"Content-Disposition,Content-Type,Cache-Control\"         \n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /api/auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','37ab2b7c2cd2b21ac12967d6d5767fd6','2023-09-27 17:43:23','2023-09-27 09:43:24','nacos','0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,50,'application-dev.yml','DEFAULT_GROUP','','spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml','d70a09aa7a6501589265f206042fb0b3','2023-09-27 19:21:06','2023-09-27 11:21:07','nacos','0:0:0:0:0:0:0:1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(9,51,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: anynote\n    password: Anynote*1832\n  redis:\n    host: localhost\n    port: 6379\n    password:\n\n','e1eee1408cdd40d139cf3dd7b147a380','2023-09-30 22:12:04','2023-09-30 14:12:04','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(46,52,'anynote-note-dev.yml','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://111.229.158.174:3306/anynote?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: anynote\r\n    password: Anynote*1832\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n\r\n','a69b9fd19d288c8c9b7ebbf7e7cc9c67','2023-09-30 22:12:42','2023-09-30 14:12:42','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,53,'anynote-file-dev.yml','DEFAULT_GROUP','','  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:','244781e698147466e31aff2e91430ba1','2023-10-02 15:56:58','2023-10-02 07:56:58',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70','');
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
INSERT INTO `users` VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1),('nacos_admin','$2a$10$YRJGfMk6R/sDxtQunQcjLe3bnySrUUht/gsL2tt7SoQE3vZY0w0gG',1);
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

-- Dump completed on 2023-10-03 13:51:07
