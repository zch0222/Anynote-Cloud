-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: anynote
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
-- Table structure for table `a_ali_green_log`
--

DROP TABLE IF EXISTS `a_ali_green_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_ali_green_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `service` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务类型',
  `content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `response` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '返回',
  `status` tinyint NOT NULL COMMENT '0.正常 1.异常',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_chat_conversation`
--

DROP TABLE IF EXISTS `a_chat_conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_chat_conversation` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '对话id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '无标题对话' COMMENT '标题',
  `type` tinyint(1) NOT NULL COMMENT '对话类型0.文档rag',
  `doc_id` bigint unsigned DEFAULT NULL COMMENT '文档ID',
  `knowledge_base_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '知识库id',
  `permissions` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '70000' COMMENT '权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_chat_message`
--

DROP TABLE IF EXISTS `a_chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_chat_message` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `conversation_id` bigint unsigned DEFAULT NULL COMMENT '对话id',
  `order_index` int NOT NULL COMMENT '排序号',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '消息',
  `role` tinyint(1) NOT NULL COMMENT '角色: 0.用户 1.bot',
  `type` tinyint(1) NOT NULL COMMENT '0.文档rag',
  `doc_id` bigint unsigned DEFAULT NULL COMMENT '文档id',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1663 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_mooc_video_summarize`
--

DROP TABLE IF EXISTS `a_mooc_video_summarize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_mooc_video_summarize` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mooc_id` bigint unsigned NOT NULL COMMENT '慕课Id',
  `mooc_item_id` bigint unsigned NOT NULL COMMENT '慕课item id',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '消息',
  `model` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模型名称',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_rag_green_log`
--

DROP TABLE IF EXISTS `a_rag_green_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_rag_green_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `conversation_id` bigint unsigned DEFAULT NULL COMMENT '对话id',
  `message_id` bigint unsigned DEFAULT NULL COMMENT '消息id',
  `rag_log_id` bigint unsigned DEFAULT NULL COMMENT 'rag日志id',
  `type` tinyint(1) NOT NULL COMMENT '0.表示用户输入内容 1.表示模型输出内容',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '文本内容',
  `risk_word` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签',
  `chinese_meaning` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文原因说明',
  `user_id` bigint unsigned DEFAULT NULL COMMENT '用户ID',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='a_rag_green_log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_rag_log`
--

DROP TABLE IF EXISTS `a_rag_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_rag_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_hash` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文档hash',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文档名称',
  `author` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类别',
  `description` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '提示词',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '机器人回复',
  `start_time` datetime NOT NULL COMMENT '查询开始时间',
  `end_time` datetime NOT NULL COMMENT '查询结束时间',
  `result` tinyint(1) NOT NULL COMMENT '查询状态 0成功 1失败',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_whisper_task`
--

DROP TABLE IF EXISTS `a_whisper_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_whisper_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `file_object_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '需要识别的文件对象名称',
  `srt_object_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字幕文件链接',
  `txt_object_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文本文件链接',
  `task_status` tinyint NOT NULL COMMENT '任务状态',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='whisper 任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_whisper_task_log`
--

DROP TABLE IF EXISTS `a_whisper_task_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_whisper_task_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'whisper 任务日志id',
  `task_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `a_whisper_task_text`
--

DROP TABLE IF EXISTS `a_whisper_task_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `a_whisper_task_text` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `whisper_task_id` bigint unsigned NOT NULL COMMENT 'whisper task id',
  `whisper_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'whisper 文本',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `a_whisper_task_text_whisper_text_id_pk` (`whisper_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='whisper 文本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `f_file`
--

DROP TABLE IF EXISTS `f_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_file` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `oss_type` char(10) NOT NULL DEFAULT '',
  `object_name` varchar(1024) NOT NULL DEFAULT '',
  `original_file_name` varchar(512) NOT NULL DEFAULT '' COMMENT '原始文件名',
  `file_name` varchar(512) NOT NULL DEFAULT '' COMMENT '文件名',
  `hash` char(128) DEFAULT NULL COMMENT '文件哈希',
  `file_size` double NOT NULL DEFAULT '0' COMMENT '文件大小',
  `url` varchar(512) NOT NULL DEFAULT '' COMMENT '文件URL地址',
  `source` tinyint(1) DEFAULT NULL COMMENT '来源 0.笔记图片 1.知识库封面 2.知识库文档 3.慕课封面',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `type` varchar(20) NOT NULL COMMENT '文件类型',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_doc`
--

DROP TABLE IF EXISTS `n_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_doc` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文档id',
  `file_id` bigint unsigned NOT NULL COMMENT '文件ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '文档名',
  `english_name` varchar(50) DEFAULT NULL,
  `knowledge_base_id` bigint unsigned NOT NULL COMMENT '知识库id',
  `type` tinyint(1) NOT NULL COMMENT '文档类型 0. PDF',
  `index_status` tinyint(1) NOT NULL COMMENT 'rag索引状态',
  `data_scope` tinyint(1) NOT NULL COMMENT '数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见',
  `permissions` char(5) NOT NULL DEFAULT '70000' COMMENT '权限(作者(创建者) 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `cover_file_id` bigint unsigned DEFAULT NULL COMMENT '知识库封面文件id',
  `detail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `type` tinyint(1) NOT NULL COMMENT '类型 (0.普通知识库 1.组织知识库)',
  `status` tinyint(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `organization_id` bigint NOT NULL DEFAULT '0' COMMENT '所属组织ID 0表示不属于任何组织',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_knowledge_base_group`
--

DROP TABLE IF EXISTS `n_knowledge_base_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_knowledge_base_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父分组id',
  `ancestors` varchar(50) NOT NULL DEFAULT '0' COMMENT '祖先分组列表',
  `group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '分组名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT '0' COMMENT '分组状态（0正常 1停用）',
  `knowledge_base_id` bigint unsigned NOT NULL COMMENT '知识库id',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_mooc`
--

DROP TABLE IF EXISTS `n_mooc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_mooc` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '慕课id',
  `title` varchar(500) NOT NULL DEFAULT '' COMMENT '慕课标题',
  `cover` varchar(512) NOT NULL DEFAULT '' COMMENT '慕课封面',
  `mooc_description` varchar(2000) NOT NULL DEFAULT '' COMMENT '慕课描述',
  `data_scope` tinyint(1) NOT NULL COMMENT '数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见',
  `knowledge_base_id` bigint DEFAULT '0' COMMENT '所属知识库id 0表示不属于任何知识库',
  `permissions` char(5) NOT NULL DEFAULT '70000' COMMENT '权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='慕课';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_mooc_item`
--

DROP TABLE IF EXISTS `n_mooc_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_mooc_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '慕课id',
  `mooc_id` bigint unsigned NOT NULL COMMENT '慕课ID',
  `title` varchar(500) NOT NULL DEFAULT '' COMMENT '慕课标题',
  `mooc_item_type` tinyint NOT NULL COMMENT '慕课类型对象 0.章节 1.视频 2.文档',
  `object_name` varchar(500) NOT NULL DEFAULT '' COMMENT '文件对象名称',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父Item id，0表示没有父Item',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='慕课item';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_mooc_item_text`
--

DROP TABLE IF EXISTS `n_mooc_item_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_mooc_item_text` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '慕课item文本',
  `mooc_item_id` bigint unsigned NOT NULL COMMENT '慕课item id',
  `item_text` text NOT NULL,
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='慕课item文本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_mooc_video_item_info`
--

DROP TABLE IF EXISTS `n_mooc_video_item_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_mooc_video_item_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '慕课视频信息id',
  `mooc_id` bigint unsigned NOT NULL COMMENT '慕课id',
  `mooc_item_id` bigint unsigned NOT NULL COMMENT '慕课',
  `srt_object_name` varchar(1000) NOT NULL DEFAULT '' COMMENT '字幕对象名称',
  `video_summarize` text COMMENT '总结',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='慕课视频信息';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2563 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_edit_log`
--

DROP TABLE IF EXISTS `n_note_edit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_edit_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编辑记录id',
  `operation_id` bigint unsigned NOT NULL COMMENT '操作id',
  `original_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始文本',
  `revised_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改后的文本',
  `change_type` int NOT NULL COMMENT '修改类型 0.修改行 1.删除行 2.插入行',
  `original_position` int NOT NULL COMMENT '原始修改位置 行号0开始',
  `revised_position` int DEFAULT NULL COMMENT '修改后的行号 0开始',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120242 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='编辑记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_file`
--

DROP TABLE IF EXISTS `n_note_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_file` (
  `note_id` bigint unsigned NOT NULL COMMENT '笔记id',
  `file_id` bigint unsigned NOT NULL COMMENT '文件id',
  `type` tinyint(1) NOT NULL COMMENT '文件类型 0.图片',
  PRIMARY KEY (`note_id`,`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_history`
--

DROP TABLE IF EXISTS `n_note_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `operation_id` bigint unsigned NOT NULL COMMENT '操作id',
  `note_id` bigint unsigned DEFAULT NULL COMMENT '笔记id',
  `title` varchar(80) NOT NULL COMMENT '笔记标题',
  `content` text NOT NULL COMMENT '正文',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  `history_time` datetime NOT NULL COMMENT '历史时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='历史记录时间';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_operation_log`
--

DROP TABLE IF EXISTS `n_note_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_operation_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `note_id` bigint unsigned NOT NULL COMMENT '笔记id',
  `operation_type` tinyint(1) NOT NULL COMMENT '操作类型 1.编辑 2.管理 3.评价',
  `operator_id` bigint unsigned NOT NULL COMMENT '操作者id',
  `operation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id_operation_type_operation_time` (`operator_id`,`operation_type`,`operation_time`)
) ENGINE=InnoDB AUTO_INCREMENT=90229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `task_describe` varchar(3000) DEFAULT NULL COMMENT '任务描述',
  `knowledge_base_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '知识库id',
  `submitted_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '已提交数量',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务状态 0.进行中 1.已结束',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除 1.删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记提交任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_task_operation_history`
--

DROP TABLE IF EXISTS `n_note_task_operation_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_task_operation_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `note_task_id` bigint unsigned NOT NULL COMMENT '笔记任务id',
  `type` tinyint(1) NOT NULL COMMENT '操作类型 1.创建 2.修改任务 3.提交任务 4.退回提交 5.添加成员',
  `operator_id` bigint unsigned NOT NULL COMMENT '操作者id',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `note_task_user_id` bigint unsigned NOT NULL COMMENT '影响的用户ID',
  `note_task_submission_record_id` bigint unsigned DEFAULT NULL COMMENT '笔记提交记录id',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除 1.删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记任务操作历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_note_task_submission_record`
--

DROP TABLE IF EXISTS `n_note_task_submission_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_note_task_submission_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `note_task_id` bigint unsigned NOT NULL COMMENT '笔记任务id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `note_id` bigint unsigned NOT NULL COMMENT '笔记id',
  `note_history_id` bigint unsigned NOT NULL COMMENT '提交的笔记历史副本id',
  `note_edit_count` bigint unsigned DEFAULT NULL COMMENT '笔记编辑次数(提交时)',
  `submit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `status` tinyint(1) NOT NULL COMMENT '记录状态 0 正常 1 被退回',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除 1.删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1413 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='笔记提交任务记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2562 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `n_user_note_task`
--

DROP TABLE IF EXISTS `n_user_note_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_user_note_task` (
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `note_task_id` bigint unsigned NOT NULL COMMENT '笔记任务id',
  `permissions` int DEFAULT '3' COMMENT '任务权限 1.管理 2.提交 3.无权限',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未提交 1.已提交 2.无需提交 3.被退回',
  PRIMARY KEY (`user_id`,`note_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_video`
--

DROP TABLE IF EXISTS `n_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_video` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '视频id',
  `file_id` bigint unsigned NOT NULL COMMENT '文件ID',
  `knowledge_base_id` bigint unsigned NOT NULL COMMENT '知识库id',
  `video_folder_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '视频文件夹id 0.不属于任何文件夹',
  `video_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频名称',
  `type` tinyint NOT NULL COMMENT '视频类型0.mp4',
  `data_scope` tinyint(1) NOT NULL COMMENT '数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见',
  `permissions` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '70000' COMMENT '权限(作者(创建者) 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `n_video_folder`
--

DROP TABLE IF EXISTS `n_video_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `n_video_folder` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '视频文件夹id',
  `folder_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件夹名称',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父文件夹id 0表示无父文件夹',
  `knowledge_base_id` bigint unsigned NOT NULL COMMENT '知识库id',
  `data_scope` tinyint(1) NOT NULL COMMENT '数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见',
  `permissions` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '70000' COMMENT '权限(作者(创建者) 知识库管理员 同知识库用户 其它用户 匿名用户)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频文件夹';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ntc_knowledge_base_notice`
--

DROP TABLE IF EXISTS `ntc_knowledge_base_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ntc_knowledge_base_notice` (
  `notice_id` bigint unsigned NOT NULL COMMENT '通知id',
  `knowledge_base_id` bigint unsigned NOT NULL COMMENT '用户id',
  PRIMARY KEY (`notice_id`,`knowledge_base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ntc_notice`
--

DROP TABLE IF EXISTS `ntc_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ntc_notice` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '通知内容',
  `type` tinyint(1) NOT NULL COMMENT '通知类型(0.个人通知 1.知识库通知)',
  `level` tinyint(1) NOT NULL DEFAULT '0' COMMENT '通知级别',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '通知状态(0.正常 1.关闭)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ntc_user_notice`
--

DROP TABLE IF EXISTS `ntc_user_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ntc_user_notice` (
  `notice_id` bigint unsigned NOT NULL COMMENT '通知id',
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未读 1.已读',
  PRIMARY KEY (`notice_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_announcement`
--

DROP TABLE IF EXISTS `sys_announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) NOT NULL DEFAULT '未命名公告' COMMENT '公告标题',
  `content` varchar(2000) NOT NULL COMMENT '公告内容',
  `date_published` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布日期',
  `author` varchar(100) NOT NULL COMMENT '发布者用户名',
  `type` varchar(50) NOT NULL COMMENT '公告类别',
  `is_pinned` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `attachment` varchar(255) DEFAULT NULL COMMENT '附件路径',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_by` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_api_statistics`
--

DROP TABLE IF EXISTS `sys_api_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_api_statistics` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `usage_count` int NOT NULL DEFAULT '0' COMMENT '调用统计',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '记录类型 0.LLM',
  `statistics_interval` tinyint NOT NULL DEFAULT '0' COMMENT '记录间隔 0.分钟 1.小时 2.天 3.周 4.月 5.年',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `start_time_end_time_type_statistics_interval_pk` (`start_time`,`end_time`,`type`,`statistics_interval`)
) ENGINE=InnoDB AUTO_INCREMENT=5368596 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='API调用统计';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_permission_rule`
--

DROP TABLE IF EXISTS `sys_permission_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission_rule` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `permission_rule_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
  `require_permission` tinyint NOT NULL COMMENT '要求的权限',
  `knowledge_base_association_type` tinyint NOT NULL COMMENT '数据库结构类型0.没有关联知识库 1. 1:1知识库id字段在实体表上 2. n:m知识库id字段在中间表上',
  `entity_table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '实体表名',
  `entity_id_field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '实体id字段名',
  `permissions_field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'permissions' COMMENT '权限字段名称',
  `knowledge_base_id_field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '知识库id字段名',
  `association_table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '实体知识库关联表名',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志(0标识未删除 1表示删除)',
  `delete_time` bigint NOT NULL DEFAULT '0' COMMENT '删除时间戳',
  `create_by` bigint unsigned DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint unsigned DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限规则表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_announcement`
--

DROP TABLE IF EXISTS `sys_user_announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `announcement_id` bigint unsigned NOT NULL COMMENT '公告ID',
  `read_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '阅读时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_announcement` (`user_id`,`announcement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-03 21:48:39
