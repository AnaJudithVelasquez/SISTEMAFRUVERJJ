-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fruveraguacates
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `COD_PRODUCT` int NOT NULL AUTO_INCREMENT,
  `COD_PURCHASE` int DEFAULT NULL,
  `PRODUCT_NAME` varchar(50) DEFAULT NULL,
  `STOCK` decimal(10,1) NOT NULL,
  `QUANTITY_Kg` decimal(10,1) NOT NULL,
  `FINAL_QUANTITY_Kg` decimal(10,1) NOT NULL,
  `PURCHASE_VALUE` decimal(10,1) NOT NULL,
  `PRODUCT_PRICE` decimal(10,1) NOT NULL,
  `DESCRIPTION_PRODUCT_STATUS` varchar(1000) NOT NULL,
  PRIMARY KEY (`COD_PRODUCT`),
  UNIQUE KEY `PRODUCT_NAME` (`PRODUCT_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (4,35,'Banano',43.5,44.5,94.9,3500.0,4000.0,'Buen estado'),(5,29,'Papa',3.5,1.0,6.6,4500.0,5000.0,'Buen estado'),(6,31,'zanahoria',3.5,3.7,56.9,4300.0,4800.0,'Buen estado'),(7,34,'Apio',3.5,150.0,145.5,3400.0,4000.0,'Producto agregado automáticamente por trigger'),(8,34,'Espinaca',45.0,90.0,87.5,3500.0,4900.0,'Producto agregado automáticamente por trigger'),(9,38,'Piña',3.5,78.0,75.5,3800.0,0.0,'Producto agregado automáticamente por trigger'),(10,39,'Repollo',3.4,20.0,40.0,5000.0,0.0,'Producto agregado automáticamente por trigger'),(11,40,'Remolacha',3.5,60.0,57.6,4000.0,0.0,'Producto agregado automáticamente desde trigger'),(12,41,'Sandia',3.5,10.4,5.9,3400.0,0.0,'Producto nuevo agregado desde trigger'),(13,42,'Uvas',3.5,40.0,35.5,3400.0,0.0,'Producto nuevo agregado desde trigger'),(14,43,'Acelga',3.5,50.0,44.4,2300.0,0.0,'Producto nuevo insertado desde trigger'),(16,46,'Frambuesa',3.5,75.0,95.0,2500.0,0.0,'Producto nuevo insertado desde trigger'),(17,49,'Fresas',3.5,60.0,57.6,2400.0,0.0,'Producto nuevo'),(18,51,'Arroz',3.5,20.0,40.0,3400.0,0.0,'Producto nuevo'),(19,53,'Chile Verde',3.5,23.0,23.0,4500.0,0.0,'Producto nuevo'),(20,54,'Tomate',3.5,4.0,0.0,5500.0,6000.0,'Buen estado'),(21,55,'Harina',3.5,6.0,6.0,4500.0,5000.0,'Producto nuevo'),(22,56,'Avena',3.5,4.0,4.0,3500.0,0.0,'Producto nuevo');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases` (
  `COD_PURCHASE` int NOT NULL AUTO_INCREMENT,
  `COD_USER` int DEFAULT NULL,
  `COD_SUPPLIER` int DEFAULT NULL,
  `DATE_PURCHASE` date DEFAULT NULL,
  `TOTAL_PURCHASE_VALUE` decimal(10,0) NOT NULL,
  PRIMARY KEY (`COD_PURCHASE`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (1,1,112,'2025-04-18',145000),(2,1,112,'2025-04-18',60000),(3,1,112,'2025-04-18',140000),(4,1,112,'2025-04-18',35000),(6,1,112,'2025-04-18',22500),(7,1,112,'2025-04-18',24150),(8,1,112,'2025-04-18',46920),(9,1,112,'2025-04-18',7000),(10,2,112,'2025-04-18',10150),(11,2,112,'2025-04-18',4600),(15,1,112,'2025-04-18',7360),(16,1,112,'2025-04-18',23400),(18,1,112,'2025-04-19',16100),(19,1,112,'2025-04-19',13300),(20,1,112,'2025-04-19',12250),(25,1,112,'2025-04-19',12250),(26,1,112,'2025-04-19',15750),(27,1,112,'2025-04-19',16800),(28,1,112,'2025-04-19',19600),(29,1,112,'2025-04-19',52410),(30,1,112,'2025-04-19',25200),(31,1,115,'2025-04-19',34610),(32,1,117,'2025-05-07',134000),(33,1,115,'2025-05-07',396900),(34,1,117,'2025-05-07',293500),(35,1,112,'2025-05-07',140000),(36,1,117,'2025-05-07',87000),(37,1,117,'2025-05-07',119000),(38,1,112,'2025-05-07',148200),(39,1,115,'2025-05-07',100000),(40,1,117,'2025-05-07',120000),(41,1,117,'2025-05-07',68000),(42,1,118,'2025-05-07',68000),(43,1,117,'2025-05-07',57500),(46,1,118,'2025-05-07',87500),(47,1,118,'2025-05-07',100000),(48,1,118,'2025-05-07',50000),(49,1,117,'2025-05-07',72000),(50,1,117,'2025-05-07',72000),(51,1,117,'2025-05-07',68000),(52,1,117,'2025-05-07',68000),(53,1,117,'2025-05-08',103500),(54,1,118,'2025-05-09',22000),(55,1,117,'2025-05-09',27000),(56,1,120,'2025-05-09',14000);
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases_details`
--

DROP TABLE IF EXISTS `purchases_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases_details` (
  `COD_PURCHASE` int DEFAULT NULL,
  `COD_PRODUCT` int DEFAULT NULL,
  `PURCHASED_PRODUCT` varchar(30) NOT NULL,
  `UNIT_VALUE` decimal(10,1) NOT NULL,
  `QUANTITY_Kg` decimal(10,1) NOT NULL,
  `TOTAL_PRODUCT` decimal(10,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases_details`
--

LOCK TABLES `purchases_details` WRITE;
/*!40000 ALTER TABLE `purchases_details` DISABLE KEYS */;
INSERT INTO `purchases_details` VALUES (8,NULL,'Banano ',2400.0,6.5,15600.0),(8,NULL,'Manzana',2900.0,10.8,31320.0),(9,NULL,'Banano',3500.0,2.0,7000.0),(10,NULL,'Manzana',2900.0,3.5,10150.0),(11,NULL,'Banano ',2300.0,2.0,4600.0),(15,NULL,'Banano',2300.0,3.2,7360.0),(16,NULL,'Banano',2300.0,4.5,10350.0),(16,NULL,'Manzana',2900.0,4.5,13050.0),(18,NULL,'Banano',3500.0,4.6,16100.0),(19,NULL,'Banano',3500.0,3.8,13300.0),(20,NULL,'Banano',3500.0,3.5,12250.0),(25,NULL,'Banano',3500.0,3.5,12250.0),(26,NULL,'Banano',3500.0,4.5,15750.0),(27,NULL,'Banano',3500.0,4.8,16800.0),(28,NULL,'Banano',3500.0,5.6,19600.0),(29,NULL,'Papa',4500.0,4.5,20250.0),(29,NULL,'Papaya',4800.0,6.7,32160.0),(30,NULL,'Papa',4500.0,5.6,25200.0),(31,NULL,'yuca',5500.0,3.4,18700.0),(31,NULL,'zanahoria',4300.0,3.7,15910.0),(32,NULL,'Arracacha',2500.0,20.0,50000.0),(32,NULL,'Aguacate',2800.0,30.0,84000.0),(33,NULL,'Zanahoria',3400.0,56.0,190400.0),(33,NULL,'Pepino',3500.0,59.0,206500.0),(34,NULL,'Apio',3400.0,40.0,136000.0),(34,NULL,'Espinaca',3500.0,45.0,157500.0),(35,NULL,'Banano',3500.0,40.0,140000.0),(36,NULL,'Manzana',2900.0,30.0,87000.0),(37,NULL,'Apio',3400.0,35.0,119000.0),(38,NULL,'Piña',3800.0,39.0,148200.0),(39,NULL,'Repollo',5000.0,20.0,100000.0),(40,NULL,'Remolacha',4000.0,30.0,120000.0),(41,NULL,'Sandia',3400.0,20.0,68000.0),(42,NULL,'Uvas',3400.0,20.0,68000.0),(43,NULL,'Acelga',2300.0,25.0,57500.0),(46,NULL,'Frambuesa',2500.0,35.0,87500.0),(47,NULL,'Frambuesa',2500.0,40.0,100000.0),(48,NULL,'Frambuesa',2500.0,20.0,50000.0),(49,NULL,'Fresas',2400.0,30.0,72000.0),(50,NULL,'Fresas',2400.0,30.0,72000.0),(51,NULL,'Arroz',3400.0,20.0,68000.0),(52,NULL,'Arroz',3400.0,20.0,68000.0),(53,NULL,'Chile',4500.0,23.0,103500.0),(54,NULL,'Tomate',5500.0,4.0,22000.0),(55,NULL,'Harina',4500.0,6.0,27000.0),(56,NULL,'Avena',3500.0,4.0,14000.0);
/*!40000 ALTER TABLE `purchases_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `COD_SALE` bigint NOT NULL AUTO_INCREMENT,
  `COD_USER` int DEFAULT NULL,
  `DATE_SALE` date DEFAULT NULL,
  `COSTUMER_IDENTIFICATION` bigint DEFAULT NULL,
  `TOTAL_SALE_VALUE` decimal(10,0) NOT NULL,
  PRIMARY KEY (`COD_SALE`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,2,'2025-04-18',1233748,80000),(2,2,'2025-04-18',123456789,145000),(3,2,'2025-04-18',2345678,25000),(4,2,'2025-04-18',23456789,20100),(5,2,'2025-04-18',345678,12000),(6,2,'2025-04-19',2345678,17500),(8,2,'2025-04-19',123456789,13440),(9,2,'2025-05-09',NULL,78200),(10,2,'2025-05-09',23456789,19120),(11,2,'2025-05-09',23456789,12920),(12,2,'2025-05-09',23456789,24050),(13,2,'2025-05-09',3456789,24800),(14,2,'2025-05-09',3456789,18640),(15,2,'2025-05-09',23456789,18000),(16,2,'2025-05-09',2345678,24000);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_details`
--

DROP TABLE IF EXISTS `sales_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_details` (
  `COD_SALE` int DEFAULT NULL,
  `COD_PRODUCT` int DEFAULT NULL,
  `PRODUCT_NAME` varchar(50) DEFAULT NULL,
  `PRODUCT_QUANTITY_Kg` decimal(10,1) NOT NULL,
  `PRODUCT_PRICE` decimal(10,0) DEFAULT NULL,
  `TOTAL_PRODUCT` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_details`
--

LOCK TABLES `sales_details` WRITE;
/*!40000 ALTER TABLE `sales_details` DISABLE KEYS */;
INSERT INTO `sales_details` VALUES (4,1,'Banano ',2.5,3000,7500),(4,2,'Manzana',3.6,3500,12600),(5,1,'Banano ',2.0,3000,6000),(5,2,'Manzana',2.0,3500,6000),(6,5,'Papa',3.5,5000,17500),(8,6,'zanahoria',2.8,4800,13440),(9,12,'Sandia',23.0,3400,78200),(10,11,'Remolacha',2.4,4000,9600),(10,12,'Sandia',2.8,3400,9520),(11,12,'Sandia',3.8,3400,12920),(12,8,'Espinaca',2.5,3500,8750),(12,13,'Uvas',4.5,3400,15300),(13,9,'Piña',2.5,3800,9500),(13,12,'Sandia',4.5,3400,15300),(14,17,'Fresas',2.4,2400,5760),(14,14,'Acelga',5.6,2300,12880),(15,7,'Apio',4.5,4000,18000),(16,20,'Tomate',4.0,6000,24000);
/*!40000 ALTER TABLE `sales_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `COD_SUPPLIER` int NOT NULL AUTO_INCREMENT,
  `SUPPLIER_NAME` varchar(50) NOT NULL,
  `ADDRESS` varchar(100) NOT NULL,
  `PHONE_NUMBER` bigint NOT NULL,
  PRIMARY KEY (`COD_SUPPLIER`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (100,'Incremento_Cod','Nulo',0),(112,'Fruticas','Av 6 # 11',3201345678),(115,'verduritas mm','calle 4',3456789900),(117,'la milagrosa yeyo','calle 23',234567890),(118,'yeyitos','calle 68',34567890),(120,'las chanclas','calle 3',34567890),(121,'Pepito','Calle 1',234567890);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `COD_USER` int NOT NULL AUTO_INCREMENT,
  `NAME_U` varchar(50) NOT NULL,
  `PASS_W` varchar(10) NOT NULL,
  `FIRST_NAME` varchar(30) NOT NULL,
  `LAST_NAME` varchar(30) NOT NULL,
  `IDENTIFICATION` bigint NOT NULL,
  `PHONE_NUMBER` bigint NOT NULL,
  `POSITION` int NOT NULL,
  `ACCOUNT_CREATION_DATE` date DEFAULT NULL,
  `RESPONSABILITY` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`COD_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin123','Ana','Judith',1000123456,3001234567,1,'2025-04-18','Administrador'),(2,'emple','emple123','Ana','Maria',1000123486,3001234067,2,'2025-05-06','Empleado');
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

-- Dump completed on 2025-05-16 21:17:07
