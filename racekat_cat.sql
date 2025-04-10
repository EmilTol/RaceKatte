CREATE DATABASE  IF NOT EXISTS `racekat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `racekat`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: racekat
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `cat`
--

DROP TABLE IF EXISTS `cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` enum('han','hun') DEFAULT NULL,
  `description` text,
  `img` varchar(255) DEFAULT NULL,
  `raceId` int DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `fk_race` (`raceId`),
  CONSTRAINT `cat_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_race` FOREIGN KEY (`raceId`) REFERENCES `race` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat`
--

LOCK TABLES `cat` WRITE;
/*!40000 ALTER TABLE `cat` DISABLE KEYS */;
INSERT INTO `cat` VALUES (7,6,'Telly',4,'hun','Telly er en lille perserkat med en blød pels, der føles som en sky, i en blanding af cremet hvid og karamel. Hendes store, runde øjne, i en iøjnefaldende ravfarve, glimter af nysgerrighed og giver hende en næsten magisk charme. Hun er knap større end en tekop og tripper rundt på fine poter med en busket hale, der svinger som en lille fjer. Telly elsker at sove i solpletter, krøllet sammen i en perfekt kugle, mens hendes spinden fylder rummet med varme. Hun jager snore med overraskende energi og tumler rundt i leg, inden hun dramatisk lægger sig ned. Hendes yndlingsplads er en blød pude, hvor hun sidder som en lille dronning.','57de72e0-a1e8-4328-9b04-9d2f74328568.jpg',2,'2025-04-10 19:21:45'),(8,6,'James',12,'han','James er en majestætisk Maine Coon på 12 år med en tæt, gråbrun pels og en imponerende størrelse. Hans store, gule øjne udstråler visdom, og hans buskede hale svinger stolt, når han bevæger sig. Selvom han er ældre, har han stadig en legende side og elsker at jage fjerstave. Han foretrækker at hvile på høje steder, hvor han kan holde øje med alt. James’ dybe spinden og rolige væsen gør ham til en ægte konge i hjemmet.','3ad5c275-a906-4040-8b4b-14937431cde4.jpg',4,'2025-04-10 19:23:30'),(9,7,'Abby',7,'hun','Abby er en smuk Ragdoll på 7 år med en silkeblød pels i creme og chokoladebrune nuancer. Hendes store, blå øjne lyser op med en mild, kærlig glød, og hun har en afslappet charme, typisk for racen. Abby elsker at slænge sig på gulvet eller i en blød kurv, hvor hun ligger helt afslappet, næsten som en kludedukke. Hun hilser altid gæster med et lille, kvidrende mjav og nyder at blive nusset under hagen. Hendes rolige, kærlige natur gør hende til en perfekt følgesvend.','f7953403-bf87-451b-90f9-c7af64b3b5e0.jpg',5,'2025-04-10 19:25:47'),(10,7,'Batman',3,'han','Batman er en elegant Russian Blue på 3 år med en kort, tæt pels i en skinnende gråblå farve, der næsten glimter i lyset. Hans smaragdgrønne øjne giver ham et intenst, næsten mystisk udtryk, og han bevæger sig med en let, lydløs grace, som en lille superhelt. Batman er fuld af energi og elsker at springe efter legetøj eller snige sig rundt i huset på jagt efter skjulte \"byttedyr\". Han har en skarp intelligens og kan åbne skabe for at udforske. Om aftenen krøller han sig sammen i en tæt kugle på sin yndlingsplads – en vindueskarm med udsigt. Hans dybe, rullende spinden og legesyge personlighed gør ham til en uimodståelig del af familien.','7005e6c4-3255-4077-b8b2-29072f8e6dfe.jpg',9,'2025-04-10 19:27:37'),(11,8,'Futte',8,'han','Futte er en yndig Birma på 8 år med en blød, cremefarvet pels og markante chokoladebrune aftegninger. Hendes dybblå øjne udstråler ro og kærlighed, og hun har en stille elegance, der passer perfekt til racen. Futte elsker at sidde på armlænet af sofaen, hvor hun holder øje med alt med mild nysgerrighed. Hun er ikke den mest legesyge længere, men nyder stadig en blød mus at bide i. Hendes sagte spinden og kælne væsen gør hende til en skattet ven.','c753f371-b212-4f16-b2fb-96c5558658e6.jpg',6,'2025-04-10 19:31:00'),(12,9,'Gonzo',8,'han','Gonzo er en storslået Ragdoll på 8 år, der fylder rummet med sin bløde, tykke pels i en blanding af flødefarvet hvid og varme, lysebrune toner. Hans store, safirblå øjne har en dybde, der næsten får en til at tro, han forstår alt, hvad der bliver sagt. Med en afslappet holdning, typisk for racen, kan Gonzo ofte findes strakt ud på gulvet eller henslængt over en stol, som om han ejer hele verden. Han har en imponerende størrelse, men hans bevægelser er langsomme og graciøse, næsten som om han svæver.\r\n\r\nSelvom han er 8 år, har Gonzo stadig en legende side. Han elsker at forsigtigt tage fat i en fjerstok med poterne eller rulle en lille bold hen over gulvet, inden han dramatisk lægger sig ned for at hvile. Hans yndlingsplads er en bred vindueskarm, hvor han kan ligge og betragte verden udenfor, mens solen varmer hans pels. Når han slapper af, er hans krop helt løs, og han lader sig villigt løfte op, hængende som en kludedukke i armene på dem, han stoler på.\r\n\r\nGonzo er en kat med personlighed. Han hilser folk med et blødt, melodisk mjav og elsker opmærksomhed, især når det kommer til at få kløet sig under hagen eller børstet sin lange pels. Han har en evne til at finde de mest behagelige steder i huset – en tyk tæppe, en blød pude eller endda en bunke nyvasket tøj. Om natten sover han tæt på sine mennesker, ofte med en pote rørende ved dem, som for at sikre sig, de er der. Hans rolige, kærlige natur og dybe, rumlende spinden gør Gonzo til mere end bare en kat – han er en trofast følgesvend, der bringer varme og glæde hver dag.','d70e5be2-d793-4de7-8981-c3af41e38d5e.jpg',5,'2025-04-10 19:33:10'),(13,9,'Misha',3,'hun','Misha er en livlig Turkish Angora på 3 år med en slank, elegant krop og en silkeblød, snehvid pels, der glimter i lyset. Hendes store, mandelformede øjne skifter mellem dybblå og grøn, afhængigt af humøret. Hun er fuld af energi og elsker at springe op på høje hylder eller jage efter lyspletter på gulvet. Misha har en nysgerrig natur og følger ofte sine mennesker rundt i huset med en logrende hale. Hendes yndlingsleg er at gemme sig og pludselig springe frem. Med sin bløde mjaven og legesyge charme er Misha en uimodståelig lille tornado.','2d4cd697-9511-4ba3-8f84-c7dd69a5c177.jpg',17,'2025-04-10 19:35:20');
/*!40000 ALTER TABLE `cat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-10 21:41:38
