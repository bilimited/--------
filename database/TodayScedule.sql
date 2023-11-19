-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.34 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 ts 的数据库结构
CREATE DATABASE IF NOT EXISTS `ts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ts`;

-- 导出  表 ts.course 结构
CREATE TABLE IF NOT EXISTS `course` (
  `cno` varchar(20) NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `college` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 ts.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `sno` varchar(20) NOT NULL DEFAULT '',
  `sname` varchar(20) DEFAULT '',
  `sex` varchar(10) DEFAULT '',
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 ts.student_course 结构
CREATE TABLE IF NOT EXISTS `student_course` (
  `sno` varchar(20) NOT NULL,
  `cno` varchar(20) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `progress` int unsigned NOT NULL DEFAULT (0),
  PRIMARY KEY (`sno`,`cno`,`semester`),
  KEY `cno` (`cno`),
  CONSTRAINT `cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
  CONSTRAINT `sno` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='student to course';

-- 数据导出被取消选择。

-- 导出  表 ts.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `tno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `tname` varchar(20) DEFAULT NULL,
  `cno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tno`) USING BTREE,
  KEY `fk_cno` (`cno`),
  CONSTRAINT `fk_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 ts.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 ts.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `uid` bigint NOT NULL DEFAULT '0',
  `sno` varchar(50) DEFAULT NULL,
  `tno` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `fk_sno` (`sno`),
  KEY `fk_tno` (`tno`),
  CONSTRAINT `fk_sno` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`),
  CONSTRAINT `fk_tno` FOREIGN KEY (`tno`) REFERENCES `teacher` (`tno`),
  CONSTRAINT `fk_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='User to Student or Teacher';

-- 数据导出被取消选择。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
