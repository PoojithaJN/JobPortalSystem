-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: job_portal
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `resume_text` varchar(255) DEFAULT NULL,
  `applicant_id` bigint DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `resume_filename` varchar(255) DEFAULT NULL,
  `resume_url` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1vur8fl2uitglu5w2wbix94fi` (`applicant_id`),
  KEY `FKls6sryk64ga8o5t4bym8qu3vm` (`job_id`),
  KEY `FKldca8xj6lqb3rsqawrowmkqbg` (`user_id`),
  CONSTRAINT `FK1vur8fl2uitglu5w2wbix94fi` FOREIGN KEY (`applicant_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKldca8xj6lqb3rsqawrowmkqbg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKls6sryk64ga8o5t4bym8qu3vm` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,NULL,NULL,1,'c47137e7-3464-4d77-8d7a-517863cbd4b6_report info.pdf',NULL,6,'2025-07-27 22:32:49.112575');
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `posted_by_id` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKepwxoimy9stpqdbebngwnptk9` (`posted_by_id`),
  KEY `FKihd6m3auwpenduntl3e1opcoq` (`user_id`),
  CONSTRAINT `FKepwxoimy9stpqdbebngwnptk9` FOREIGN KEY (`posted_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKihd6m3auwpenduntl3e1opcoq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'tcs','i need a aspiring java developer who as 2+ years experience with knowledge on current ai technologies and java tools,dsa and spring boot','whitefield','java developer',2,NULL,NULL);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `resume_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$ZRaxvFmNyFMrx1KyTMomsuutuYnRNQNJGMsft8Tl9dUdABJtiZKIi','EMPLOYER','emp1',NULL,NULL,NULL,NULL),(2,'$2a$10$RwSeqghxJq2jFdWLNWbHCeiCeOk12fgEL8L/YgQiIrwu3wFjjStOm','EMPLOYER','user1',NULL,NULL,NULL,NULL),(3,'$2a$10$efpK1kZjnKHvpsmUcMNMp.CAxseCrfzUT3oHKHN71Kc7jZvQa3B6i','APPLICANT','user2',NULL,NULL,NULL,NULL),(4,'$2a$10$jsPjdzVVN.SouOeU626ycux8t.hvKHjPGi7qh8z79Ibsu2q53FQVi','EMPLOYER','emp2',NULL,NULL,NULL,NULL),(5,'$2a$10$BbScz1yon8TJS7XwGC4K6uWA55qtZi1MPnMxEN/UE6RpwAeVugnUW','APPLICANT','user@gmail.com',NULL,'user',NULL,NULL),(6,'$2a$10$/RZKemQcuszeeSJ7CvaVo.54y1wZXI6g4Jcy67D9.jQtXh8Hquz4i','APPLICANT','john',NULL,'john nick','9887654345',NULL),(7,'$2a$10$Yi20QNGb9l91VcSWSVQchOkEWYQiijULQFpqtqAB3Zk1uHthc2fny','EMPLOYER','dick','dick@gmail.com','dick',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-28 19:14:02
