-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: alguser
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_branch_transaction`
--

DROP TABLE IF EXISTS `tb_branch_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_branch_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `args_json` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1，初始化；2，已提交；3，已回滚',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiy_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `xid` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='事务记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_branch_transaction`
--

LOCK TABLES `tb_branch_transaction` WRITE;
/*!40000 ALTER TABLE `tb_branch_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_branch_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_city`
--

DROP TABLE IF EXISTS `tb_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(50) NOT NULL COMMENT '城市名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='开业城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_city`
--

LOCK TABLES `tb_city` WRITE;
/*!40000 ALTER TABLE `tb_city` DISABLE KEYS */;
INSERT INTO `tb_city` VALUES (1,'厦门');
/*!40000 ALTER TABLE `tb_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `full_name` varchar(50) NOT NULL COMMENT '企业名称',
  `short_name` varchar(20) NOT NULL COMMENT '企业简称',
  `link_phone` varchar(15) NOT NULL COMMENT '联系电话',
  `status` int(11) DEFAULT '1' COMMENT '0:无效  1:有效',
  `province_id` int(11) NOT NULL COMMENT '省份',
  `city_id` int(11) NOT NULL COMMENT '城市id',
  `address` varchar(100) NOT NULL COMMENT '详细地址',
  `delivery_time` varchar(20) NOT NULL COMMENT '每日送餐时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='企业表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--

LOCK TABLES `tb_company` WRITE;
/*!40000 ALTER TABLE `tb_company` DISABLE KEYS */;
INSERT INTO `tb_company` VALUES (1,'难吃的公司','难吃','18888888888',1,1,1,'难吃路难吃街','','2020-07-22 08:37:15');
/*!40000 ALTER TABLE `tb_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户姓名',
  `profile_pic` varchar(100) DEFAULT NULL COMMENT '头像url',
  `sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别',
  `link_phone` varchar(15) NOT NULL COMMENT '联系电话',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1' COMMENT '0: 冻结, 1:正常',
  `company_id` bigint(20) NOT NULL COMMENT '用户所属公司id',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `password` char(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (2,'fuhua.zhang',NULL,1,'17859735221',NULL,'2020-07-22 14:14:03',1,1,NULL,'e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_amount`
--

DROP TABLE IF EXISTS `tb_user_amount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_amount` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户金额',
  `create_time` datetime DEFAULT NULL COMMENT '账户创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户账户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_amount`
--

LOCK TABLES `tb_user_amount` WRITE;
/*!40000 ALTER TABLE `tb_user_amount` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_amount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_amount_tx`
--

DROP TABLE IF EXISTS `tb_user_amount_tx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_amount_tx` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `xid` varchar(50) NOT NULL COMMENT '事务id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `frozenAmount` decimal(10,2) DEFAULT NULL COMMENT '冻结的金额',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0等待处理  -1等待回滚  -2回滚成功  1等待提交 2提交成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `roll_back_time` datetime DEFAULT NULL COMMENT '回滚时间',
  `order_id` bigint(20) NOT NULL COMMENT '对应的订单id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_idx_xid` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户账户金额tcc事务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_amount_tx`
--

LOCK TABLES `tb_user_amount_tx` WRITE;
/*!40000 ALTER TABLE `tb_user_amount_tx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_amount_tx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_company`
--

DROP TABLE IF EXISTS `tb_user_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_company` (
  `user_id` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_company`
--

LOCK TABLES `tb_user_company` WRITE;
/*!40000 ALTER TABLE `tb_user_company` DISABLE KEYS */;
INSERT INTO `tb_user_company` VALUES (2,1);
/*!40000 ALTER TABLE `tb_user_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_integral`
--

DROP TABLE IF EXISTS `tb_user_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_integral` (
  `id` bigint(20) NOT NULL,
  `type` int(11) DEFAULT NULL COMMENT '事件类型',
  `user_id` bigint(20) NOT NULL,
  `integral` int(11) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0' COMMENT '0初始化 1已发送',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户积分临时记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_integral`
--

LOCK TABLES `tb_user_integral` WRITE;
/*!40000 ALTER TABLE `tb_user_integral` DISABLE KEYS */;
INSERT INTO `tb_user_integral` VALUES (1,NULL,1,1,'2020-08-09 21:34:36','2020-08-12 21:29:34',1,123),(2,NULL,1,2,'2020-08-09 21:34:36','2020-08-12 21:29:34',1,125),(3,NULL,2,3,'2020-08-09 21:34:36','2020-08-12 21:29:34',1,122),(4,NULL,2,3,'2020-08-09 21:34:36','2020-08-12 21:29:34',1,123456),(5,NULL,2,3,'2020-08-09 21:34:36','2020-08-12 21:29:34',1,123457);
/*!40000 ALTER TABLE `tb_user_integral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_integral_record`
--

DROP TABLE IF EXISTS `tb_user_integral_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_integral_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `integral` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_integral_record`
--

LOCK TABLES `tb_user_integral_record` WRITE;
/*!40000 ALTER TABLE `tb_user_integral_record` DISABLE KEYS */;
INSERT INTO `tb_user_integral_record` VALUES (2,1,3,'2020-08-12 21:29:17'),(3,2,9,'2020-08-12 21:29:17');
/*!40000 ALTER TABLE `tb_user_integral_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-02 20:14:02
