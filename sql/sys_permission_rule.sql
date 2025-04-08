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
-- Dumping data for table `sys_permission_rule`
--

LOCK TABLES `sys_permission_rule` WRITE;
/*!40000 ALTER TABLE `sys_permission_rule` DISABLE KEYS */;
INSERT INTO `sys_permission_rule` VALUES (1,'ndoc:read',4,1,'n_doc','id','permissions','knowledge_base_id','',0,0,0,'2024-07-28 15:17:39',0,'2024-07-28 15:17:39',''),(2,'a:chatConversation:read',4,1,'a_chat_conversation','id','permissions','knowledge_base_id','',0,0,0,'2024-07-28 21:25:26',0,'2024-07-28 21:25:26',''),(3,'a:chatConversation:completions',6,1,'a_chat_conversation','id','permissions','knowledge_base_id','',0,0,0,'2024-07-30 00:01:00',0,'2024-07-30 00:01:00',''),(4,'a:chatConversation:update',6,1,'a_chat_conversation','id','permissions','knowledge_base_id','',0,0,0,'2024-07-30 00:16:51',0,'2024-07-30 00:16:51',''),(5,'n:mooc:update',6,1,'n_mooc','id','permissions','knowledge_base_id','',0,0,0,'2025-02-05 00:39:04',0,'2025-02-05 00:39:04',''),(6,'n:mooc:read',4,1,'n_mooc','id','permissions','knowledge_base_id','',0,0,0,'2025-02-05 00:42:06',0,'2025-02-05 00:42:06',''),(7,'n:mooc:manage',7,1,'n_mooc','id','permissions','knowledge_base_id','',0,0,0,'2025-03-29 17:04:23',0,'2025-03-29 17:04:23',''),(8,'a:chatConversation:manage',7,1,'a_chat_conversation','id','permissions','knowledge_base_id','',0,0,0,'2025-04-08 12:51:48',0,'2025-04-08 12:51:48','');
/*!40000 ALTER TABLE `sys_permission_rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-08 13:14:27
