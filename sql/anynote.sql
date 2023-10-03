-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: anynote
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
-- Table structure for table `f_note_image`
--

DROP TABLE IF EXISTS `f_note_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_note_image` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `original_file_name` varchar(512) NOT NULL DEFAULT '' COMMENT '原始文件名',
  `file_name` varchar(512) NOT NULL DEFAULT '' COMMENT '文件名',
  `url` varchar(512) NOT NULL DEFAULT '' COMMENT '文件URL地址',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_note_image`
--

LOCK TABLES `f_note_image` WRITE;
/*!40000 ALTER TABLE `f_note_image` DISABLE KEYS */;
INSERT INTO `f_note_image` VALUES (1,'sticker.webp','b3c7a48c0199489ea8fa45f2b9481b44_sticker.webp','https://anynote.obs.cn-east-3.myhuaweicloud.com:443/anynote_+Shanghai%2Fnote%2F1%2Fb3c7a48c0199489ea8fa45f2b9481b44_sticker.webp',0,0,'2023-10-02 19:12:39',0,'2023-10-02 19:12:39','',2),(2,'sticker.webp','9008fa12ce514ec99e9f65c23149f11c_sticker.webp','https://anynote.obs.cn-east-3.myhuaweicloud.com:443/anynote_+Shanghai%2Fnote%2F1%2F9008fa12ce514ec99e9f65c23149f11c_sticker.webp',0,0,'2023-10-02 19:16:42',0,'2023-10-02 19:16:42','',2),(3,'sticker.webp','fe66f3f8f0874b159fd90c4f20a5a369_sticker.webp','https://anynote.obs.cn-east-3.myhuaweicloud.com:443/anynote_+Shanghai%2Fnote%2F1%2Ffe66f3f8f0874b159fd90c4f20a5a369_sticker.webp',0,0,'2023-10-02 19:19:30',0,'2023-10-02 19:19:30','',2),(4,'sticker.webp','0bd9fab94080453c8c7b2dd8563b2052_sticker.webp','https://anynote.obs.cn-east-3.myhuaweicloud.com:443/anynote_+Shanghai%2Fnote%2F1%2F0bd9fab94080453c8c7b2dd8563b2052_sticker.webp',0,0,'2023-10-02 22:02:45',0,'2023-10-02 22:02:45','',2);
/*!40000 ALTER TABLE `f_note_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_knowledge_base`
--

DROP TABLE IF EXISTS `n_knowledge_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_knowledge_base` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识库id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '知识库名称',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '知识库封面',
  `detail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `type` tinyint(1) NOT NULL COMMENT '类型 (0.普通知识库 1.课程知识库)',
  `status` tinyint(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `organization_id` bigint NOT NULL DEFAULT '0' COMMENT '所属组织ID 0表示不属于任何组织',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_knowledge_base`
--

LOCK TABLES `n_knowledge_base` WRITE;
/*!40000 ALTER TABLE `n_knowledge_base` DISABLE KEYS */;
INSERT INTO `n_knowledge_base` VALUES (1,'测试知识库1','https://anynote.obs.cn-east-3.myhuaweicloud.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230427135917.jpg',NULL,0,0,0,0,'2023-09-27 18:02:26',0,'2023-09-27 18:02:26','1',1),(2,'测试知识库2','https://anynote.obs.cn-east-3.myhuaweicloud.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230427135917.jpg',NULL,0,0,0,0,'2023-09-27 18:02:40',0,'2023-09-27 18:02:40','2',1),(3,'测试知识库3','https://anynote.obs.cn-east-3.myhuaweicloud.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230427135917.jpg',NULL,0,0,0,0,'2023-09-29 15:59:54',0,'2023-09-29 15:59:54','',0),(4,'测试知识库1001','https://i0.hdslb.com/bfs/new_dyn/18d03597c3df44d36c50891685d8d3153494361880856991.jpg@662w_560h_1e_1c.avif',NULL,0,0,0,2,'2023-10-01 23:51:13',2,'2023-10-01 23:51:13','',0);
/*!40000 ALTER TABLE `n_knowledge_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_note`
--

DROP TABLE IF EXISTS `n_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '笔记id',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '未命名笔记' COMMENT '笔记标题',
  `note_text_id` bigint unsigned NOT NULL COMMENT '笔记正文id',
  `knowledge_base_id` bigint DEFAULT '0' COMMENT '所属知识库id 0表示不属于任何知识库',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0.正常 1.垃圾桶',
  `data_scope` tinyint(1) NOT NULL COMMENT '数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见',
  `permissions` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '70000' COMMENT '权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_note`
--

LOCK TABLES `n_note` WRITE;
/*!40000 ALTER TABLE `n_note` DISABLE KEYS */;
INSERT INTO `n_note` VALUES (1,'修改测试14445',1,2,0,2,'70000',0,2,'2023-09-30 00:45:55',2,'2023-10-01 00:57:01',''),(2,'修改测试14445',2,2,0,2,'70600',0,1,'2023-09-30 00:58:51',2,'2023-10-01 00:56:45',''),(4,'知识库2的测试笔记3',3,3,0,1,'70000',0,1,'2023-09-30 01:09:29',0,'2023-09-30 01:09:29',''),(5,'知识库2的测试笔记4',1,2,0,3,'70000',0,1,'2023-09-30 01:10:10',0,'2023-09-30 01:10:10',''),(6,'测试笔记',3,3,0,1,'70000',1,2,'2023-10-01 20:31:09',2,'2023-10-01 20:31:08',''),(7,'修改测试14445',4,3,0,1,'44000',0,2,'2023-10-01 20:32:51',2,'2023-10-01 21:36:18',''),(8,'测试笔记23333',5,3,0,1,'44000',0,2,'2023-10-01 21:28:40',2,'2023-10-01 21:28:40','');
/*!40000 ALTER TABLE `n_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_note_task`
--

DROP TABLE IF EXISTS `n_note_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '笔记任务id',
  `task_name` varchar(256) NOT NULL DEFAULT '' COMMENT '任务名称',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务开始时间',
  `end_time` datetime NOT NULL COMMENT '任务结束时间',
  `knowledge_base_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '知识库',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务状态 0.进行中 1.已结束',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除 1.删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记提交任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_note_task`
--

LOCK TABLES `n_note_task` WRITE;
/*!40000 ALTER TABLE `n_note_task` DISABLE KEYS */;
INSERT INTO `n_note_task` VALUES (1,'测试任务','2023-09-27 00:00:00','2023-10-28 00:00:00',3,0,0,0,'2023-10-02 21:39:33',0,'2023-10-02 21:39:33',''),(2,'测试任务2','2023-09-27 00:00:00','2023-10-28 00:00:00',3,0,0,2,'2023-10-03 13:44:09',2,'2023-10-03 13:44:09','');
/*!40000 ALTER TABLE `n_note_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_note_task_submission_record`
--

DROP TABLE IF EXISTS `n_note_task_submission_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_task_submission_record` (
  `note_task_id` bigint unsigned NOT NULL COMMENT '笔记任务id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `note_id` bigint unsigned NOT NULL COMMENT '笔记id',
  `submit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除 1.删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`note_task_id`,`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记提交任务记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_note_task_submission_record`
--

LOCK TABLES `n_note_task_submission_record` WRITE;
/*!40000 ALTER TABLE `n_note_task_submission_record` DISABLE KEYS */;
INSERT INTO `n_note_task_submission_record` VALUES (1,2,7,'2023-10-03 13:34:42',0,2,'2023-10-03 13:34:42',2,'2023-10-03 13:34:42',''),(2,2,8,'2023-10-03 13:44:17',0,2,'2023-10-03 13:44:17',2,'2023-10-03 13:44:17','');
/*!40000 ALTER TABLE `n_note_task_submission_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_note_text`
--

DROP TABLE IF EXISTS `n_note_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_text` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '正文id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '笔记正文',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_note_text`
--

LOCK TABLES `n_note_text` WRITE;
/*!40000 ALTER TABLE `n_note_text` DISABLE KEYS */;
INSERT INTO `n_note_text` VALUES (1,'666664411111',0,2,'2023-09-30 00:45:19',2,'2023-10-01 00:57:01',''),(2,'666664411111',0,0,'2023-09-30 21:59:25',2,'2023-10-01 00:56:45',''),(3,'# 测试笔记',1,2,'2023-10-01 20:31:09',2,'2023-10-01 20:31:09',''),(4,'666664411111',0,2,'2023-10-01 20:32:51',2,'2023-10-01 21:36:18',''),(5,'# 测试笔记23333',0,2,'2023-10-01 21:28:40',2,'2023-10-01 21:28:40','');
/*!40000 ALTER TABLE `n_note_text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_organization_knowledge_base`
--

DROP TABLE IF EXISTS `n_organization_knowledge_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_organization_knowledge_base` (
  `organization_id` bigint NOT NULL COMMENT '组织id',
  `knowledge_base_id` bigint NOT NULL COMMENT '知识库id',
  PRIMARY KEY (`organization_id`,`knowledge_base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_organization_knowledge_base`
--

LOCK TABLES `n_organization_knowledge_base` WRITE;
/*!40000 ALTER TABLE `n_organization_knowledge_base` DISABLE KEYS */;
INSERT INTO `n_organization_knowledge_base` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `n_organization_knowledge_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `n_user_knowledge_base`
--

DROP TABLE IF EXISTS `n_user_knowledge_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_user_knowledge_base` (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `knowledge_base_id` bigint NOT NULL COMMENT '知识库ID',
  `permissions` tinyint(1) NOT NULL COMMENT '数据权限 (1.可管理 2.可编辑 3.可阅读 4.无权限)',
  PRIMARY KEY (`user_id`,`knowledge_base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户知识库关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `n_user_knowledge_base`
--

LOCK TABLES `n_user_knowledge_base` WRITE;
/*!40000 ALTER TABLE `n_user_knowledge_base` DISABLE KEYS */;
INSERT INTO `n_user_knowledge_base` VALUES (2,1,1),(2,2,2),(2,3,1);
/*!40000 ALTER TABLE `n_user_knowledge_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '配置id',
  `name` varchar(50) NOT NULL COMMENT '配置名称',
  `value` varchar(2048) NOT NULL COMMENT '配置值',
  `description` varchar(128) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'OSS_TYPE','HUAWEI_OBS','对象存储类别'),(2,'HUAWEI_OBS_CONFIG','{\n	\"endPoint\": \"obs.cn-east-3.myhuaweicloud.com\",\n	\"accessKey\": \"RJZU7OJSW0LVYMIF3LYC\",\n	\"accessSecret\": \"kRzDrTMhz7gbngzFDeDFyAV3CB58h1k88ZUN7HRx\",\n	\"bucketName\": \"anynote\",\n	\"basePath\": \"anynote_ Shanghai\"\n}','华为对象存储配置');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '前端路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前端组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由参数',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` tinyint(1) NOT NULL DEFAULT '0' COMMENT '菜单显示（0显示 1隐藏）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后端地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_organization` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父组织id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '祖先列表',
  `organization_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '组织名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
INSERT INTO `sys_organization` VALUES (1,0,'0','测试组织',1,NULL,NULL,NULL,0,0,0,'2023-09-27 18:01:36',0,NULL,'测试组织');
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_key` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(1) DEFAULT '5' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅自己数据）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色状态',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记，1表示删除，0表示未删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'AdminX','ADMIN_X',1,1,'0',0,0,NULL,0,NULL,NULL),(2,'student','STUDENT',2,5,'0',0,0,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和菜单关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户邮箱',
  `phone_number` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记，1表示删除，0表示未删除',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'adminX','adminX','yxlmzch0222@gmail.com','19883526582',1,'','$2a$10$gNx2Qrg1HgUkjukwQwmDc.H0hHXWk9KJYJNZkV2YjuHRYwr6LGt3m',0,0,'','2023-09-26 12:07:31',0,NULL,0,NULL,NULL),(2,'testStudent','testStudent','','',0,'','$2a$10$gNx2Qrg1HgUkjukwQwmDc.H0hHXWk9KJYJNZkV2YjuHRYwr6LGt3m',0,0,'',NULL,0,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_organization`
--

DROP TABLE IF EXISTS `sys_user_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_organization` (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `organization_id` bigint NOT NULL COMMENT '组织id1',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态  (0.正常 1.退出)',
  PRIMARY KEY (`user_id`,`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组织表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_organization`
--

LOCK TABLES `sys_user_organization` WRITE;
/*!40000 ALTER TABLE `sys_user_organization` DISABLE KEYS */;
INSERT INTO `sys_user_organization` VALUES (1,1,0);
/*!40000 ALTER TABLE `sys_user_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '角色ID',
  `role_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-03 13:47:18
