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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'OSS_TYPE','MIN_IO','对象存储类别'),(2,'HUAWEI_OBS_CONFIG','{\"endPoint\": \"obs.cn-east-3.myhuaweicloud.com\",\"accessKey\": \"\",\"accessSecret\": \"\",\"bucketName\": \"anynote\",\"basePath\": \"anynote_Shanghai_one\"}','华为对象存储配置'),(3,'AI_SERVER_ADDRESS','http://anynote-langchain',''),(4,'TRANSLATE_TYPE','DEEPL',''),(5,'DEEPL_CONFIG','{\"textTranslateEndPoint\": \"https://api-free.deepl.com/v2/translate\",\"token\": \"\"}',''),(6,'RAG_MAX_DAY_COUNT','50',''),(7,'HOME_DOC_ID','50','首页展示文档'),(8,'GREEN_TYPE','ALI_GREEN','内容安全检测类型'),(9,'ALI_GREEN_CONFIG','{\"endpoint\": \"green-cip.cn-shanghai.aliyuncs.com\",\"alibabaCloudAccessKeyId\": \"\",\"alibabaCloudAccessKeySecret\": \"\"}','阿里云内容安全检测配置'),(10,'AI_SERVER_API_KEY','',''),(11,'MIN_IO_CONFIG','{\"endPoint\": \"https://api.minio.yypan.xyz\",\"accessKey\": \"\",\"secretKey\": \"\",\"bucketName\": \"anynote\",\"basePath\": \"anynote_Shanghai_one\"}','MinIO配置'),(12,'WHISPER_CONFIG','{\"baseUrl\": \"\",\"apiKey\": \"\", \"tmpFileFolder\": \"/Users/zch/code/anynote/tmpWhisperFolder\"}','');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-08 13:13:59
