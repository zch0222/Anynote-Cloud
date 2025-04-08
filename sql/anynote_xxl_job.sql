-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: anynote_xxl_job
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
-- Table structure for table `xxl_job_group`
--

DROP TABLE IF EXISTS `xxl_job_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_group`
--

LOCK TABLES `xxl_job_group` WRITE;
/*!40000 ALTER TABLE `xxl_job_group` DISABLE KEYS */;
INSERT INTO `xxl_job_group` VALUES (1,'xxl-job-executor-sample','示例执行器',0,NULL,'2025-04-06 19:14:05'),(2,'anynote-job','anynote-job',0,'http://192.168.10.124:9999/','2025-04-06 19:14:05');
/*!40000 ALTER TABLE `xxl_job_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_info`
--

DROP TABLE IF EXISTS `xxl_job_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_info`
--

LOCK TABLES `xxl_job_info` WRITE;
/*!40000 ALTER TABLE `xxl_job_info` DISABLE KEYS */;
INSERT INTO `xxl_job_info` VALUES (1,1,'测试任务1','2018-11-03 22:21:31','2018-11-03 22:21:31','XXL','','CRON','0 0 0 * * ? *','DO_NOTHING','FIRST','demoJobHandler','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2018-11-03 22:21:31','',0,0,0),(2,2,'慕课ARS','2025-03-15 15:25:05','2025-03-15 15:25:05','admin','','NONE','','DO_NOTHING','FIRST','moocVideoARS','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2025-03-15 15:25:05','',0,0,0),(3,2,'新建下一天大模型调用次数记录','2025-03-31 01:36:44','2025-03-31 01:42:15','admin','','CRON','0 30 23 * * ?','DO_NOTHING','FIRST','llmStatisticsDailyCreate','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2025-03-31 01:36:44','',1,1743867000000,1743953400000);
/*!40000 ALTER TABLE `xxl_job_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_lock`
--

DROP TABLE IF EXISTS `xxl_job_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_lock`
--

LOCK TABLES `xxl_job_lock` WRITE;
/*!40000 ALTER TABLE `xxl_job_lock` DISABLE KEYS */;
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');
/*!40000 ALTER TABLE `xxl_job_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log`
--

DROP TABLE IF EXISTS `xxl_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`),
  KEY `I_jobid_jobgroup` (`job_id`,`job_group`),
  KEY `I_job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log`
--

LOCK TABLES `xxl_job_log` WRITE;
/*!40000 ALTER TABLE `xxl_job_log` DISABLE KEYS */;
INSERT INTO `xxl_job_log` VALUES (1,1,1,NULL,'demoJobHandler','',NULL,0,'2025-03-13 17:08:26',500,'任务触发类型：手动触发<br>调度机器：192.168.1.190<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(2,2,2,NULL,'moocVideoARS','TEST',NULL,0,'2025-03-15 15:26:12',500,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(3,2,2,'http://192.168.100.20:9999/','moocVideoARS','Test',NULL,0,'2025-03-15 15:26:45',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:26:45',200,'',0),(4,2,2,'http://192.168.100.20:9999/','moocVideoARS','',NULL,0,'2025-03-15 15:27:01',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:27:01',200,'',0),(5,2,2,'http://192.168.100.20:9999/','moocVideoARS','{\"objectName\": \"test\"}',NULL,0,'2025-03-15 15:27:16',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:27:16',200,'',0),(6,2,2,'http://192.168.100.20:9999/','moocVideoARS','TEST',NULL,0,'2025-03-15 15:32:15',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:32:15',200,'',0),(7,2,2,'http://192.168.100.20:9999/','moocVideoARS','{\"moocItemId\": 9999}',NULL,0,'2025-03-15 15:38:42',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:38:42',200,'',0),(8,2,2,'http://192.168.100.20:9999/','moocVideoARS','{\"moocItemId\": 9999}',NULL,0,'2025-03-15 15:39:07',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 15:39:07',200,'',0),(9,2,2,'http://192.168.100.20:9999/','moocVideoARS','{}',NULL,0,'2025-03-15 16:12:07',200,'任务触发类型：手动触发<br>调度机器：192.168.100.20<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.100.20:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.100.20:9999/<br>code：200<br>msg：null','2025-03-15 16:12:07',200,'',0),(10,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:36:56',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:36:56',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.FeignException$NotFound: [404 Not Found] during [POST] to [http://anynote-ai-nio/llmStatistics] [RemoteLlmStatisticsService#createLlmStatistics(String,LlmStatisticsCreateDTO)]: [{\"timestamp\":\"2025-03-30T17:36:55.822+00:00\",\"path\":\"/llmStatistics\",\"status\":404,\"error\":\"Not Found\",\"message\":null,\"requestId\":\"26e2ceb9-2\"}]\n	at feign.FeignException.clientErrorStatus(FeignException.java:219)\n	at feign.FeignException.errorStatus(FeignException.java:194)\n	at feign.FeignException.errorStatus(FeignException.java:185)\n	at feign.codec.ErrorDecoder$Default.decode(ErrorDecoder.java:92)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:98)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:27)\n	... 6 more\n',2),(11,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:38:08',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:38:08',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.FeignException$NotFound: [404 Not Found] during [POST] to [http://anynote-ai-nio/llmStatistics] [RemoteLlmStatisticsService#createLlmStatistics(String,LlmStatisticsCreateDTO)]: [{\"timestamp\":\"2025-03-30T17:38:08.412+00:00\",\"path\":\"/llmStatistics\",\"status\":404,\"error\":\"Not Found\",\"message\":null,\"requestId\":\"b6eece40-3\"}]\n	at feign.FeignException.clientErrorStatus(FeignException.java:219)\n	at feign.FeignException.errorStatus(FeignException.java:194)\n	at feign.FeignException.errorStatus(FeignException.java:185)\n	at feign.codec.ErrorDecoder$Default.decode(ErrorDecoder.java:92)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:98)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:28)\n	... 6 more\n',2),(12,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:40:00',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:40:00',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.codec.DecodeException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at feign.InvocationContext.proceed(InvocationContext.java:40)\n	at feign.AsyncResponseHandler.decode(AsyncResponseHandler.java:116)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:89)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:28)\n	... 6 more\nCaused by: org.springframework.web.client.RestClientException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:120)\n	at org.springframework.cloud.openfeign.support.SpringDecoder.decode(SpringDecoder.java:75)\n	at org.springframework.cloud.openfeign.support.ResponseEntityDecoder.decode(ResponseEntityDecoder.java:61)\n	at feign.optionals.OptionalDecoder.decode(OptionalDecoder.java:36)\n	at feign.InvocationContext.proceed(InvocationContext.java:36)\n	... 13 more\nCaused by: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:391)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:105)\n	... 17 more\nCaused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)\n	at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1741)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1515)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1420)\n	at com.fasterxml.jackson.databind.DeserializationContext.extractScalarFromObject(DeserializationContext.java:932)\n	at com.fasterxml.jackson.databind.deser.std.StdDeserializer._parseLong(StdDeserializer.java:949)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:575)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:550)\n	at com.fasterxml.jackson.databind.deser.impl.MethodProperty.deserializeAndSet(MethodProperty.java:129)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)\n	at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)\n	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4674)\n	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3682)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:380)\n	... 19 more\n',2),(13,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:41:47',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:41:48',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.codec.DecodeException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at feign.InvocationContext.proceed(InvocationContext.java:40)\n	at feign.AsyncResponseHandler.decode(AsyncResponseHandler.java:116)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:89)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:30)\n	... 6 more\nCaused by: org.springframework.web.client.RestClientException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:120)\n	at org.springframework.cloud.openfeign.support.SpringDecoder.decode(SpringDecoder.java:75)\n	at org.springframework.cloud.openfeign.support.ResponseEntityDecoder.decode(ResponseEntityDecoder.java:61)\n	at feign.optionals.OptionalDecoder.decode(OptionalDecoder.java:36)\n	at feign.InvocationContext.proceed(InvocationContext.java:36)\n	... 13 more\nCaused by: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:391)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:105)\n	... 17 more\nCaused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)\n	at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1741)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1515)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1420)\n	at com.fasterxml.jackson.databind.DeserializationContext.extractScalarFromObject(DeserializationContext.java:932)\n	at com.fasterxml.jackson.databind.deser.std.StdDeserializer._parseLong(StdDeserializer.java:949)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:575)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:550)\n	at com.fasterxml.jackson.databind.deser.impl.MethodProperty.deserializeAndSet(MethodProperty.java:129)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)\n	at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)\n	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4674)\n	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3682)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:380)\n	... 19 more\n',2),(14,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:42:20',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:42:20',200,'',0),(15,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-03-31 01:42:44',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-03-31 01:42:44',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.codec.DecodeException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at feign.InvocationContext.proceed(InvocationContext.java:40)\n	at feign.AsyncResponseHandler.decode(AsyncResponseHandler.java:116)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:89)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:30)\n	... 6 more\nCaused by: org.springframework.web.client.RestClientException: Error while extracting response for type [com.anynote.core.web.model.bo.ResData<java.lang.Long>] and content type [application/json]; nested exception is org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:120)\n	at org.springframework.cloud.openfeign.support.SpringDecoder.decode(SpringDecoder.java:75)\n	at org.springframework.cloud.openfeign.support.ResponseEntityDecoder.decode(ResponseEntityDecoder.java:61)\n	at feign.optionals.OptionalDecoder.decode(OptionalDecoder.java:36)\n	at feign.InvocationContext.proceed(InvocationContext.java:36)\n	... 13 more\nCaused by: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:391)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:343)\n	at org.springframework.web.client.HttpMessageConverterExtractor.extractData(HttpMessageConverterExtractor.java:105)\n	... 17 more\nCaused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.lang.Long` from Object value (token `JsonToken.START_OBJECT`)\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 66] (through reference chain: com.anynote.core.web.model.bo.ResData[\"data\"])\n	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)\n	at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1741)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1515)\n	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1420)\n	at com.fasterxml.jackson.databind.DeserializationContext.extractScalarFromObject(DeserializationContext.java:932)\n	at com.fasterxml.jackson.databind.deser.std.StdDeserializer._parseLong(StdDeserializer.java:949)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:575)\n	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$LongDeserializer.deserialize(NumberDeserializers.java:550)\n	at com.fasterxml.jackson.databind.deser.impl.MethodProperty.deserializeAndSet(MethodProperty.java:129)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)\n	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)\n	at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)\n	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4674)\n	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3682)\n	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:380)\n	... 19 more\n',2),(16,2,3,NULL,'llmStatisticsDailyCreate','',NULL,0,'2025-03-31 23:30:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(17,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-04-05 23:30:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-04-05 23:30:00',500,'java.lang.reflect.InvocationTargetException\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:31)\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:166)\nCaused by: feign.FeignException$ServiceUnavailable: [503] during [POST] to [http://anynote-ai-nio/llmStatistics] [RemoteLlmStatisticsService#createLlmStatistics(String,LlmStatisticsCreateDTO)]: [Load balancer does not contain an instance for the service anynote-ai-nio]\n	at feign.FeignException.serverErrorStatus(FeignException.java:256)\n	at feign.FeignException.errorStatus(FeignException.java:197)\n	at feign.FeignException.errorStatus(FeignException.java:185)\n	at feign.codec.ErrorDecoder$Default.decode(ErrorDecoder.java:92)\n	at feign.AsyncResponseHandler.handleResponse(AsyncResponseHandler.java:98)\n	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:141)\n	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:91)\n	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:100)\n	at com.sun.proxy.$Proxy139.createLlmStatistics(Unknown Source)\n	at com.anynote.jobhandler.LlmStatisticsHandler.llmStatisticsDailyCreate(LlmStatisticsHandler.java:30)\n	... 6 more\n',2),(18,2,3,'http://192.168.10.124:9999/','llmStatisticsDailyCreate','',NULL,0,'2025-04-06 09:41:10',200,'任务触发类型：手动触发<br>调度机器：192.168.10.124<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.10.124:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.10.124:9999/<br>code：200<br>msg：null','2025-04-06 09:41:11',200,'',0);
/*!40000 ALTER TABLE `xxl_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log_report`
--

DROP TABLE IF EXISTS `xxl_job_log_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_log_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log_report`
--

LOCK TABLES `xxl_job_log_report` WRITE;
/*!40000 ALTER TABLE `xxl_job_log_report` DISABLE KEYS */;
INSERT INTO `xxl_job_log_report` VALUES (1,'2025-03-13 00:00:00',0,0,1,NULL),(2,'2025-03-12 00:00:00',0,0,0,NULL),(3,'2025-03-11 00:00:00',0,0,0,NULL),(4,'2025-03-15 00:00:00',0,7,1,NULL),(5,'2025-03-14 00:00:00',0,0,0,NULL),(6,'2025-03-16 00:00:00',0,0,0,NULL),(7,'2025-03-17 00:00:00',0,0,0,NULL),(8,'2025-03-18 00:00:00',0,0,0,NULL),(9,'2025-03-30 00:00:00',0,0,0,NULL),(10,'2025-03-29 00:00:00',0,0,0,NULL),(11,'2025-03-28 00:00:00',0,0,0,NULL),(12,'2025-03-31 00:00:00',0,1,6,NULL),(13,'2025-04-01 00:00:00',0,0,0,NULL),(14,'2025-04-05 00:00:00',0,0,1,NULL),(15,'2025-04-04 00:00:00',0,0,0,NULL),(16,'2025-04-03 00:00:00',0,0,0,NULL),(17,'2025-04-06 00:00:00',0,1,0,NULL);
/*!40000 ALTER TABLE `xxl_job_log_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_logglue`
--

DROP TABLE IF EXISTS `xxl_job_logglue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_logglue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_logglue`
--

LOCK TABLES `xxl_job_logglue` WRITE;
/*!40000 ALTER TABLE `xxl_job_logglue` DISABLE KEYS */;
/*!40000 ALTER TABLE `xxl_job_logglue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_registry`
--

DROP TABLE IF EXISTS `xxl_job_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_registry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_registry`
--

LOCK TABLES `xxl_job_registry` WRITE;
/*!40000 ALTER TABLE `xxl_job_registry` DISABLE KEYS */;
INSERT INTO `xxl_job_registry` VALUES (3360,'EXECUTOR','anynote-job','http://192.168.10.124:9999/','2025-04-06 19:13:34');
/*!40000 ALTER TABLE `xxl_job_registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_user`
--

DROP TABLE IF EXISTS `xxl_job_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_user`
--

LOCK TABLES `xxl_job_user` WRITE;
/*!40000 ALTER TABLE `xxl_job_user` DISABLE KEYS */;
INSERT INTO `xxl_job_user` VALUES (1,'admin','e10adc3949ba59abbe56e057f20f883e',1,NULL);
/*!40000 ALTER TABLE `xxl_job_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-08 13:18:33
