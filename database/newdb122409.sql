-- --------------------------------------------------------
-- 主机:                           
-- 服务器版本:                        8.0.34 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 ts1 的数据库结构
CREATE DATABASE IF NOT EXISTS `ts1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ts1`;user

-- 导出  表 ts1.course 结构
CREATE TABLE IF NOT EXISTS `course` (
  `cno` bigint NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) DEFAULT NULL,
  `dept` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tno` bigint DEFAULT NULL,
  `day` int DEFAULT '1',
  `start` int DEFAULT '1',
  `end` int DEFAULT '1',
  `room` varchar(50) DEFAULT NULL,
  `note` text,
  `capacity` int DEFAULT '1',
  `progress` int DEFAULT '0',
  PRIMARY KEY (`cno`),
  KEY `fk_ct_tno` (`tno`),
  CONSTRAINT `fk_ct_tno` FOREIGN KEY (`tno`) REFERENCES `teacher` (`tno`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='day:这节课在周几(1-7)\r\nstart:这节课在第几节开始(1-11)\r\nend:这节课在第几节结束(1-11)\r\nroom:这节课在哪个教室\r\nnote:这节课的备注\r\ncapacity:这节课可选的最大人数';

-- 数据导出被取消选择。

-- 导出  函数 ts1.course_stunumber 结构
DELIMITER //
CREATE FUNCTION `course_stunumber`(
	`in_cno` VARCHAR(50)
) RETURNS int
    DETERMINISTIC
BEGIN
	DECLARE snumber INT;
	SELECT COUNT(*) INTO snumber FROM student_course WHERE cno=in_cno;
	RETURN snumber;
END//
DELIMITER ;

-- 导出  视图 ts1.course_view 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `course_view` (
	`cno` BIGINT(19) NOT NULL,
	`cname` VARCHAR(20) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`dept` VARCHAR(20) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`tname` VARCHAR(50) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`day` INT(10) NULL,
	`start` INT(10) NULL,
	`end` INT(10) NULL,
	`room` VARCHAR(50) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`note` TEXT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`capacity` INT(10) NULL,
	`stunumber` INT(10) NULL,
	`progress` INT(10) NULL
) ENGINE=MyISAM;

-- 导出  表 ts1.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `sno` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生档案，包括该学生的学号等信息。';

-- 数据导出被取消选择。

-- 导出  表 ts1.student_course 结构
CREATE TABLE IF NOT EXISTS `student_course` (
  `sno` bigint NOT NULL DEFAULT (0),
  `cno` bigint NOT NULL DEFAULT (0),
  `semester` varchar(20) NOT NULL,
  PRIMARY KEY (`sno`,`cno`,`semester`),
  KEY `fk_scc_cno` (`cno`),
  CONSTRAINT `fk_scc_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
  CONSTRAINT `fk_scs` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='student to course';

-- 数据导出被取消选择。

-- 导出  表 ts1.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `tno` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师档案';

-- 数据导出被取消选择。

-- 导出  表 ts1.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `realname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `portraitid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='portraitid是头像的id\r\n';

-- 数据导出被取消选择。

-- 导出  表 ts1.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `uid` bigint NOT NULL DEFAULT '0',
  `sno` bigint DEFAULT NULL,
  `tno` bigint DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `fk_th` (`tno`),
  KEY `fk_stu` (`sno`),
  CONSTRAINT `fk_stu` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`),
  CONSTRAINT `fk_th` FOREIGN KEY (`tno`) REFERENCES `teacher` (`tno`),
  CONSTRAINT `fk_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='User to Student or Teacher';

-- 数据导出被取消选择。

-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `course_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `course_view` AS select `course`.`cno` AS `cno`,`course`.`cname` AS `cname`,`course`.`dept` AS `dept`,`user`.`realname` AS `tname`,`course`.`day` AS `day`,`course`.`start` AS `start`,`course`.`end` AS `end`,`course`.`room` AS `room`,`course`.`note` AS `note`,`course`.`capacity` AS `capacity`,`course_stunumber`(`course`.`cno`) AS `stunumber`,`course`.`progress` AS `progress` from (((`course` join `teacher` on((`course`.`tno` = `teacher`.`tno`))) join `user_role` on((`course`.`tno` = `user_role`.`tno`))) join `user` on((`user_role`.`uid` = `user`.`uid`)));

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
