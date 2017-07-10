-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: fi_dev
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER` (
  `OPEN_ID` varchar(64) NOT NULL,
  `NICK_NAME` varchar(64) NOT NULL,
  `GENDER` smallint(6) DEFAULT NULL,
  `CITY` varchar(64) DEFAULT NULL,
  `PROVINCE` varchar(128) DEFAULT NULL,
  `COUNTRY` varchar(128) DEFAULT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_LOGIN_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`OPEN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER` DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES ('111111','刘力',1,NULL,NULL,NULL,'2017-07-03 02:00:09','2017-07-07 13:45:52'),('19830310007','liuli',1,NULL,NULL,NULL,'2017-06-25 20:14:06','2017-07-02 01:34:50');
/*!40000 ALTER TABLE `CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ENTERPRISE`
--

DROP TABLE IF EXISTS `ENTERPRISE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ENTERPRISE` (
  `CREDIT_CODE` varchar(20) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  `TYPE` varchar(128) DEFAULT NULL,
  `LEGAL_PERSON` varchar(64) DEFAULT NULL,
  `REG_CAPITAL` decimal(10,2) DEFAULT NULL,
  `ESTABLISH_DATE` date DEFAULT NULL,
  `BIZ_PERIOD_START` date DEFAULT NULL,
  `BIZ_PERIOD_END` date DEFAULT NULL,
  `REG_AUTHORITY` varchar(128) DEFAULT NULL,
  `ADDRESS` varchar(256) NOT NULL,
  `MAIN_BIZ` blob,
  `PHONE` varchar(16) NOT NULL,
  `BANK` varchar(256) DEFAULT NULL,
  `BANK_ACCT` varchar(32) DEFAULT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(64) NOT NULL,
  `MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFY_BY` varchar(64) DEFAULT NULL,
  `BIZ_REG_NUM` varchar(20) DEFAULT NULL COMMENT '工商注册号',
  `ORG_CODE` varchar(20) DEFAULT NULL COMMENT '组织结构代码',
  `TAXPAYER_CODE` varchar(20) DEFAULT NULL COMMENT '纳税人识别号',
  `INDUSTRY` varchar(64) DEFAULT NULL COMMENT '行业',
  PRIMARY KEY (`CREDIT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ENTERPRISE`
--

LOCK TABLES `ENTERPRISE` WRITE;
/*!40000 ALTER TABLE `ENTERPRISE` DISABLE KEYS */;
INSERT INTO `ENTERPRISE` VALUES ('91210200241281392F','大连万达集团股份有限公司',NULL,'王健林',100000.00,'1992-01-27','1992-01-27','2037-01-27','大连市工商行政管理局','辽宁省大连市西岗区长江路539号',NULL,'010-85853888',NULL,NULL,'2017-07-09 18:24:57','system','2017-07-09 18:24:57','system','210200000021906','241281392','210200241281392','房地产业'),('未公开','北京智在必享网络科技有限公司',NULL,'唐振庭',20.00,'2012-01-28','2012-01-28','2032-01-27','东城分局','北京市东城区崇文门外大街11号409室',NULL,'18910554360',NULL,NULL,'2017-07-09 18:24:12','system','2017-07-09 18:24:12','system','110101015278410','055597852','未公开','科技推广和应用服务业');
/*!40000 ALTER TABLE `ENTERPRISE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ENTERPRISE_MODIFY_HISTORY`
--

DROP TABLE IF EXISTS `ENTERPRISE_MODIFY_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ENTERPRISE_MODIFY_HISTORY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREDIT_CODE` varchar(20) NOT NULL,
  `MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MODIFY_BY` varchar(64) NOT NULL,
  `FIELD` varchar(32) NOT NULL,
  `OLD_VALUE` varchar(255) NOT NULL,
  `NEW_VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ENTERPRISE_HISTORY` (`CREDIT_CODE`),
  CONSTRAINT `FK_ENTERPRISE_HISTORY` FOREIGN KEY (`CREDIT_CODE`) REFERENCES `ENTERPRISE` (`CREDIT_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ENTERPRISE_MODIFY_HISTORY`
--

LOCK TABLES `ENTERPRISE_MODIFY_HISTORY` WRITE;
/*!40000 ALTER TABLE `ENTERPRISE_MODIFY_HISTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ENTERPRISE_MODIFY_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `INVOICE`
--

DROP TABLE IF EXISTS `INVOICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INVOICE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPEN_ID` varchar(64) NOT NULL,
  `TYPE` smallint(6) NOT NULL,
  `USER_NAME` varchar(32) DEFAULT NULL,
  `CREDIT_CODE` varchar(32) DEFAULT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IS_DEFAULT` smallint(6) NOT NULL,
  `STATUS` smallint(6) NOT NULL COMMENT '0-有效,1-无效',
  PRIMARY KEY (`ID`),
  KEY `FK_INVOICE_ENTERPRISE` (`CREDIT_CODE`),
  KEY `FK_CUSTOMER_INVOICE` (`OPEN_ID`),
  CONSTRAINT `FK_CUSTOMER_INVOICE` FOREIGN KEY (`OPEN_ID`) REFERENCES `CUSTOMER` (`OPEN_ID`),
  CONSTRAINT `FK_INVOICE_ENTERPRISE` FOREIGN KEY (`CREDIT_CODE`) REFERENCES `ENTERPRISE` (`CREDIT_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INVOICE`
--

LOCK TABLES `INVOICE` WRITE;
/*!40000 ALTER TABLE `INVOICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `INVOICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MERCHANT`
--

DROP TABLE IF EXISTS `MERCHANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MERCHANT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `TYPE` smallint(6) NOT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `PHONE1` varchar(32) DEFAULT NULL,
  `PHONE2` varchar(32) DEFAULT NULL,
  `LONGITUDE` decimal(10,3) NOT NULL,
  `LETITUDE` decimal(10,3) NOT NULL,
  `ZIP_CODE` varchar(10) DEFAULT NULL,
  `STATUS` smallint(6) NOT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` varchar(32) NOT NULL,
  `MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFY_BY` varchar(32) DEFAULT NULL,
  `EXPIRE_TIME` timestamp NOT NULL DEFAULT '2036-12-31 15:59:59',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MERCHANT`
--

LOCK TABLES `MERCHANT` WRITE;
/*!40000 ALTER TABLE `MERCHANT` DISABLE KEYS */;
INSERT INTO `MERCHANT` VALUES (1,'a',1,'adress',NULL,NULL,NULL,3.100,3.200,NULL,1,'2017-07-06 03:52:10','1','2017-07-06 03:52:10','1','2017-07-06 03:52:10'),(2,'a',1,'adress',NULL,NULL,NULL,3.100,3.200,NULL,1,'2017-07-06 03:50:49','1','2017-07-06 03:50:49','1','2017-07-06 03:50:49');
/*!40000 ALTER TABLE `MERCHANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MERCHANT_INVOICE`
--

DROP TABLE IF EXISTS `MERCHANT_INVOICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MERCHANT_INVOICE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` int(11) NOT NULL,
  `INVOICE_ID` int(11) NOT NULL,
  `AMOUNT` decimal(10,2) DEFAULT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_INVOICE_MERCHAT` (`INVOICE_ID`),
  KEY `FK_MERCHAT_INVOICE` (`MERCHANT_ID`),
  CONSTRAINT `FK_INVOICE_MERCHAT` FOREIGN KEY (`INVOICE_ID`) REFERENCES `INVOICE` (`ID`),
  CONSTRAINT `FK_MERCHAT_INVOICE` FOREIGN KEY (`MERCHANT_ID`) REFERENCES `MERCHANT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MERCHANT_INVOICE`
--

LOCK TABLES `MERCHANT_INVOICE` WRITE;
/*!40000 ALTER TABLE `MERCHANT_INVOICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `MERCHANT_INVOICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MERCHANT_TYPE`
--

DROP TABLE IF EXISTS `MERCHANT_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MERCHANT_TYPE` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `REMARK` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MERCHANT_TYPE`
--

LOCK TABLES `MERCHANT_TYPE` WRITE;
/*!40000 ALTER TABLE `MERCHANT_TYPE` DISABLE KEYS */;
INSERT INTO `MERCHANT_TYPE` VALUES (1,'超市','');
/*!40000 ALTER TABLE `MERCHANT_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_EXCEPTION`
--

DROP TABLE IF EXISTS `SYS_EXCEPTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SYS_EXCEPTION` (
  `CLASS` varchar(255) NOT NULL,
  `CODE` varchar(10) NOT NULL,
  PRIMARY KEY (`CLASS`,`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SYS_EXCEPTION`
--

LOCK TABLES `SYS_EXCEPTION` WRITE;
/*!40000 ALTER TABLE `SYS_EXCEPTION` DISABLE KEYS */;
INSERT INTO `SYS_EXCEPTION` VALUES ('com.changyi.fi.exception.NullRequestException','101');
/*!40000 ALTER TABLE `SYS_EXCEPTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_PARAMETER`
--

DROP TABLE IF EXISTS `SYS_PARAMETER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SYS_PARAMETER` (
  `CODE` varchar(64) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `VALUE` varchar(64) NOT NULL,
  `REMARK` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SYS_PARAMETER`
--

LOCK TABLES `SYS_PARAMETER` WRITE;
/*!40000 ALTER TABLE `SYS_PARAMETER` DISABLE KEYS */;
INSERT INTO `SYS_PARAMETER` VALUES ('MERCHANT_VALIDATION_TOGGLE','开启商户验证','true',''),('MERCHANT_VALID_DISTANCE','商户有效距离','20',''),('TOKEN_EXPIRATION_SECONDS','令牌超时时间','3600',''),('TOKEN_REPOSITORY_IMPL_CLZ','令牌资源库实现类','com.changyi.fi.core.token.TokenRepository','');
/*!40000 ALTER TABLE `SYS_PARAMETER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_invoice`
--

DROP TABLE IF EXISTS `v_invoice`;
/*!50001 DROP VIEW IF EXISTS `v_invoice`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_invoice` AS SELECT 
 1 AS `ID`,
 1 AS `OPEN_ID`,
 1 AS `TYPE`,
 1 AS `USER_NAME`,
 1 AS `CREDIT_CODE`,
 1 AS `CREATE_TIME`,
 1 AS `MODIFY_TIME`,
 1 AS `IS_DEFAULT`,
 1 AS `STATUS`,
 1 AS `CORP_NAME`,
 1 AS `ADDRESS`,
 1 AS `PHONE`,
 1 AS `BANK`,
 1 AS `BANK_ACCT`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_invoice`
--

/*!50001 DROP VIEW IF EXISTS `v_invoice`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_invoice` AS select `I`.`ID` AS `ID`,`I`.`OPEN_ID` AS `OPEN_ID`,`I`.`TYPE` AS `TYPE`,`I`.`USER_NAME` AS `USER_NAME`,`I`.`CREDIT_CODE` AS `CREDIT_CODE`,`I`.`CREATE_TIME` AS `CREATE_TIME`,`I`.`MODIFY_TIME` AS `MODIFY_TIME`,`I`.`IS_DEFAULT` AS `IS_DEFAULT`,`I`.`STATUS` AS `STATUS`,`E`.`NAME` AS `CORP_NAME`,`E`.`ADDRESS` AS `ADDRESS`,`E`.`PHONE` AS `PHONE`,`E`.`BANK` AS `BANK`,`E`.`BANK_ACCT` AS `BANK_ACCT` from (`invoice` `I` left join `enterprise` `E` on((`I`.`CREDIT_CODE` = `E`.`CREDIT_CODE`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-10 15:28:32
