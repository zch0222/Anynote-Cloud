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
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8mb3_bin,
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` VALUES (1,'anynote-gateway-dev.yml','DEFAULT_GROUP','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\n        # 认证模块\n        - id: anynote-auth\n          uri: lb://anynote-auth\n          predicates:\n            - Path=/api/auth/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','561b71f3c1f1e511c7064a548b10602d','2023-07-26 03:10:00','2023-07-28 06:29:21','','192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','',''),(9,'anynote-system-dev.yml','DEFAULT_GROUP','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 08:56:01','2023-07-27 08:56:01',NULL,'192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70',NULL,NULL,NULL,'yaml',NULL,''),(24,'application-dev.yml','DEFAULT_GROUP','spring:\n#   autoconfigure:\n#     exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n\n# mybatis-plus配置\nmybatis-plus:\n  configuration:\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\n  global-config:\n    db-config:\n      id-type: auto\n  mapper-locations: classpath:mapper/*.xml\n\nanynote:\n  jwt-setting:\n    # token过期时间单位是分钟\n    tokenExpireTime: 30\n    secret: yxlm','8d8f8ef5b4ea53953efb6fa602b4ac7e','2023-07-28 09:20:40','2023-08-08 03:10:02','','192.168.186.1','','0587fa28-1301-43db-a7a1-599c00fc3f70','','','','yaml','','');
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
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
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
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
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
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
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
  `tag_name` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
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
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
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
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8mb3_bin,
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (0,1,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          lowerCaseServiceId: true\r\n          enabled: true\r\n\r\n# 安全配置\r\nsecurity:\r\n  # 验证码\r\n  captcha:\r\n    enabled: true\r\n    type: math\r\n  # 防止XSS攻击\r\n  xss:\r\n    enabled: true\r\n    excludeUrls:\r\n      - /system/notice\r\n  # 不校验白名单\r\n  ignore:\r\n    whites:\r\n      - /auth/logout\r\n      - /auth/login\r\n      - /auth/register\r\n      - /*/v2/api-docs\r\n      - /csrf','bd4cd436602080cdd148a9232311bd0c','2023-07-26 11:10:00','2023-07-26 03:10:00',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,2,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          lowerCaseServiceId: true\r\n          enabled: true\r\n\r\n# 安全配置\r\nsecurity:\r\n  # 验证码\r\n  captcha:\r\n    enabled: true\r\n    type: math\r\n  # 防止XSS攻击\r\n  xss:\r\n    enabled: true\r\n    excludeUrls:\r\n      - /system/notice\r\n  # 不校验白名单\r\n  ignore:\r\n    whites:\r\n      - /auth/logout\r\n      - /auth/login\r\n      - /auth/register\r\n      - /*/v2/api-docs\r\n      - /csrf','bd4cd436602080cdd148a9232311bd0c','2023-07-26 13:52:33','2023-07-26 05:52:34','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,3,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\ntest:\n  name: \"anynote-gateway\"\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','0f702ec53d039b48a15b77f2e79bc3a3','2023-07-26 14:00:34','2023-07-26 06:00:34','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,4,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\nanynote:\n  moudlue:\n    name: \'anynote-gateway\'\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','6c7c347900a7c70ef7f56067f019819d','2023-07-26 14:05:17','2023-07-26 06:05:17','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,5,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\nanynote:\n  module:\n    name: \'anynote-gateway\'\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','fb7fd35b3a5efdf6795a9398cccde749','2023-07-26 14:06:53','2023-07-26 06:06:53','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,6,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\nanynote:\n  module:\n    name: \'anynote-gateway\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','595e6377e8eb13d6c1570b5e75b0b837','2023-07-26 14:33:52','2023-07-26 06:33:53','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,7,'anynote-system','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 16:55:01','2023-07-27 08:55:01',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(7,8,'anynote-system','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 16:55:11','2023-07-27 08:55:12',NULL,'192.168.186.1','D','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,9,'anynote-system-dev','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 16:55:29','2023-07-27 08:55:29',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(8,10,'anynote-system-dev','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 16:55:40','2023-07-27 08:55:41',NULL,'192.168.186.1','D','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,11,'anynote-system-dev.yml','DEFAULT_GROUP','','# 数据库配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/kindergarten?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: 123456','2c704c607ebdeb3d8ef81d5c5dbb2996','2023-07-27 16:56:01','2023-07-27 08:56:01',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,12,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','14f3d7145ce932c50596e2eceed1a631','2023-07-27 17:13:08','2023-07-27 09:13:09','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,13,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n    routes:       \n      - id: anynote-system\n        uri: lb://anynote-system\n        predicates:\n          - Path=/api/system/**\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','e18a31ed24295fc23966a976ed099bf7','2023-07-28 08:58:30','2023-07-28 00:58:30','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,14,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n    routes:       \n      - id: anynote-system\n        uri: http://localhost:8081\n        predicates:\n          - Path=/api/system/**\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf','7d8730dbf993f20cae3b420c2350b454','2023-07-28 09:00:45','2023-07-28 01:00:45','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,15,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n    routes:       \n      - id: anynote-system\n        uri: http://localhost:8081\n        predicates:\n          - Path=/api/system/**\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','1843ae995910fe401b04c3b053132595','2023-07-28 09:25:01','2023-07-28 01:25:01','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,16,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n    routes:       \n      - id: anynote-system\n        uri: lb://anynote-system\n        predicates:\n          - Path=/api/system/**\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','5fee17c60aa4d725287694eea4bd52f5','2023-07-28 09:28:33','2023-07-28 01:28:33','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,17,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','bdd0fba79c150429d58e14a258bfad4c','2023-07-28 09:29:29','2023-07-28 01:29:29','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,18,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: ruoyi-system\n          uri: lb://ruoyi-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','4425c04f0685be4a5d3e841eca3f2f64','2023-07-28 09:29:59','2023-07-28 01:30:00','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,19,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: ruoyi-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','9cd7f9d1d3316c9b3353719e22355c02','2023-07-28 09:30:20','2023-07-28 01:30:20','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,20,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: ruoyi-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/system/**\n          # filters:\n          #   - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','fb0671ebcb999e51fa0de2fdc679ae1b','2023-07-28 09:30:44','2023-07-28 01:30:45','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,21,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','396950daefdaa572ccc32e2f1adb13e9','2023-07-28 09:30:55','2023-07-28 01:30:55','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,22,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','313ab2dcd8d92d608e4ba1d7bf467459','2023-07-28 09:31:23','2023-07-28 01:31:24','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,23,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      - /api/system/*','396950daefdaa572ccc32e2f1adb13e9','2023-07-28 09:33:14','2023-07-28 01:33:15','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,24,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=1\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','f293c7d3789473d54b3a282136125e0c','2023-07-28 09:36:05','2023-07-28 01:36:06','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(1,25,'anynote-gateway-dev.yml','DEFAULT_GROUP','','spring:\n  redis:\n    host: localhost\n    port: 6379\n    password:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:       \n        # 系统模块\n        - id: anynote-system\n          uri: lb://anynote-system\n          predicates:\n            - Path=/api/system/**\n          filters:\n            - StripPrefix=2\nanynote:\n  module:\n    name: \'anynote-gateway1\'\n\n# 测试属性\nruoyi:\n  # 名称\n  name: RuoYi\n  # 版本\n  version: 1.0.0\n\n# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    enabled: true\n    type: math\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/logout\n      - /auth/login\n      - /auth/register\n      - /*/v2/api-docs\n      - /csrf\n      # - /api/system/*','d338f677bd17d62c117beab42dd6184a','2023-07-28 14:29:20','2023-07-28 06:29:21','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(0,26,'application-dev.yml','DEFAULT_GROUP','','spring:\r\n#   autoconfigure:\r\n#     exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\r\n  mvc:\r\n    pathmatch:\r\n      matching-strategy: ant_path_matcher\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  configuration:\r\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\r\n    map-underscore-to-camel-case: true\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n  mapper-locations: classpath:mapper/*.xml\r\n','94bdcda28c56f7f16a8448767c59bcae','2023-07-28 17:20:40','2023-07-28 09:20:40',NULL,'192.168.186.1','I','0587fa28-1301-43db-a7a1-599c00fc3f70',''),(24,27,'application-dev.yml','DEFAULT_GROUP','','spring:\r\n#   autoconfigure:\r\n#     exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\r\n  mvc:\r\n    pathmatch:\r\n      matching-strategy: ant_path_matcher\r\n\r\n# mybatis-plus配置\r\nmybatis-plus:\r\n  configuration:\r\n    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射\r\n    map-underscore-to-camel-case: true\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  global-config:\r\n    db-config:\r\n      id-type: auto\r\n  mapper-locations: classpath:mapper/*.xml\r\n','94bdcda28c56f7f16a8448767c59bcae','2023-08-08 11:10:01','2023-08-08 03:10:02','','192.168.186.1','U','0587fa28-1301-43db-a7a1-599c00fc3f70','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `action` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `tenant_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
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
  `kp` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` VALUES (1,'1','0587fa28-1301-43db-a7a1-599c00fc3f70','dev','开发环境','nacos',1690340127460,1690340127460);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);
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

-- Dump completed on 2023-08-13 22:10:47
