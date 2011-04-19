# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: 127.0.0.1 (MySQL 5.1.46)
# Database: forum
# Generation Time: 2011-04-19 14:10:48 +0200
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table board
# ------------------------------------------------------------

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `boardId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `categoryId` int(10) unsigned NOT NULL,
  `title` varchar(512) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`boardId`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` (`boardId`,`categoryId`,`title`,`description`)
VALUES
	(1,1,'General','General questions'),
	(2,2,'(X)HTML','Structuring websites'),
	(4,2,'CSS','Styling websites'),
	(5,3,'PHP','Hypertext Preprocessor'),
	(6,3,'JSF','Java Server Faces');

/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `categoryId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(512) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`categoryId`,`title`)
VALUES
	(1,'General'),
	(2,'Coding'),
	(3,'Programming');

/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `replyId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `threadId` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL,
  `content` text NOT NULL,
  `createdOn` datetime NOT NULL,
  PRIMARY KEY (`replyId`),
  KEY `threadId` (`threadId`,`userId`),
  KEY `userId` (`userId`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`threadId`) REFERENCES `thread` (`threadId`) ON DELETE CASCADE,
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`replyId`,`threadId`,`userId`,`content`,`createdOn`)
VALUES
	(1,2,3,'I like the technology behind this forum!','2011-04-04 23:09:14'),
	(2,2,1,'Ich Holz','2011-04-11 14:20:44'),
	(3,2,1,'trololol','2011-04-12 14:29:31'),
	(4,2,1,'jojojo','2011-04-01 16:10:11'),
	(5,4,1,'dsfsdfdsf','2011-04-15 21:43:13'),
	(6,8,1,'Sali','2011-04-19 10:44:52'),
	(7,8,1,'dwfwefewf','2011-04-19 10:53:34');

/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table thread
# ------------------------------------------------------------

DROP TABLE IF EXISTS `thread`;

CREATE TABLE `thread` (
  `threadId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `boardId` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL,
  `title` varchar(512) NOT NULL,
  `content` text NOT NULL,
  `createdOn` datetime NOT NULL,
  PRIMARY KEY (`threadId`),
  KEY `boardId` (`boardId`),
  KEY `userId` (`userId`),
  CONSTRAINT `thread_ibfk_1` FOREIGN KEY (`boardId`) REFERENCES `board` (`boardId`) ON DELETE CASCADE,
  CONSTRAINT `thread_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

LOCK TABLES `thread` WRITE;
/*!40000 ALTER TABLE `thread` DISABLE KEYS */;
INSERT INTO `thread` (`threadId`,`boardId`,`userId`,`title`,`content`,`createdOn`)
VALUES
	(2,1,1,'Do you like this forum?','So, what do you say? Do you like the result of this project?','2011-04-04 18:35:24'),
	(3,1,2,'How can I write a thread???','Can anybody tell me how I can write a new thread?','2011-04-05 23:08:10'),
	(4,1,1,'TEST','testsdadasd','2011-04-15 21:43:00'),
	(5,1,1,'wwwawwa','wwww','2011-04-15 22:13:35'),
	(6,1,1,'sadsadsad','sadasdsadsa','2011-04-16 12:54:54'),
	(7,2,1,'XHTMLAsiajsd','jakhsdkajshdsadasdsadsa','2011-04-16 12:55:09'),
	(8,1,1,'ewfewfew','fewfewfewf','2011-04-19 10:42:47');

/*!40000 ALTER TABLE `thread` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(100) NOT NULL,
  `website` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dateOfBirth` datetime DEFAULT NULL,
  `permission` int(10) unsigned DEFAULT '0',
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `createdOn` datetime NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userId`,`username`,`password`,`email`,`website`,`location`,`firstName`,`lastName`,`dateOfBirth`,`permission`,`active`,`createdOn`)
VALUES
	(1,'david','172522ec1028ab781d9dfd17eaca4427','davidknezic@gmail.com','www.davidknezic.com','Winterthur (ZH), Swintzerland','David','Knezic','1994-01-28 00:00:00',0,1,'2011-00-00 00:00:00'),
	(2,'marcello','de239033eaac1ffb65f83ab6e4dd0981','marcel@bloeckli.ch',NULL,NULL,NULL,NULL,NULL,0,1,'0000-00-00 00:00:00'),
	(3,'eniu','3346a536dd4a2c2340ea25c92a40c0d4','forum@eniu.ch',NULL,NULL,NULL,NULL,NULL,0,1,'0000-00-00 00:00:00'),
	(4,'blocked','61326117ed4a9ddf3f754e71e119e5b3','block@me.now',NULL,NULL,NULL,NULL,NULL,0,0,'0000-00-00 00:00:00'),
	(5,'master','eb0a191797624dd3a48fa681d3061212','master@master.xyz',NULL,NULL,NULL,NULL,NULL,0,1,'0000-00-00 00:00:00');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;





/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
