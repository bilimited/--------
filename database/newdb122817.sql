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


-- 导出 ts1 的数据库结构
CREATE DATABASE IF NOT EXISTS `ts1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ts1`;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='day:这节课在周几(1-7)\r\nstart:这节课在第几节开始(1-11)\r\nend:这节课在第几节结束(1-11)\r\nroom:这节课在哪个教室\r\nnote:这节课的备注\r\ncapacity:这节课可选的最大人数';

-- 正在导出表  ts1.course 的数据：~0 rows (大约)
DELETE FROM `course`;
INSERT INTO `course` (`cno`, `cname`, `dept`, `tno`, `day`, `start`, `end`, `room`, `note`, `capacity`, `progress`) VALUES
	(11, '原神', '电子竞技', 11, 1, 5, 4, '114', '原神           启动！！！！！！！！！！！！！', 514, 100),
	(12, '明日方舟', '电竞', 11, 4, 5, 6, '5', '', 3, 0),
	(13, 'bukexvan', 'bukexvan', 11, 0, 0, 0, '0', '', 0, 0);

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
	`tno` BIGINT(19) NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生档案，包括该学生的学号等信息。';

-- 正在导出表  ts1.student 的数据：~4 rows (大约)
DELETE FROM `student`;
INSERT INTO `student` (`sno`) VALUES
	(4),
	(5),
	(6),
	(7);

-- 导出  表 ts1.student_course 结构
CREATE TABLE IF NOT EXISTS `student_course` (
  `sno` bigint NOT NULL DEFAULT (0),
  `cno` bigint NOT NULL DEFAULT (0),
  `semester` int NOT NULL DEFAULT (0),
  PRIMARY KEY (`sno`,`cno`) USING BTREE,
  KEY `fk_scc_cno` (`cno`),
  CONSTRAINT `fk_scc_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
  CONSTRAINT `fk_scs` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='student to course';

-- 正在导出表  ts1.student_course 的数据：~2 rows (大约)
DELETE FROM `student_course`;
INSERT INTO `student_course` (`sno`, `cno`, `semester`) VALUES
	(6, 11, 0),
	(7, 11, 0),
	(7, 12, 0);

-- 导出  表 ts1.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `tno` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师档案';

-- 正在导出表  ts1.teacher 的数据：~2 rows (大约)
DELETE FROM `teacher`;
INSERT INTO `teacher` (`tno`) VALUES
	(10),
	(11);

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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='portraitid是头像的id\r\n';

-- 正在导出表  ts1.user 的数据：~7 rows (大约)
DELETE FROM `user`;
INSERT INTO `user` (`uid`, `username`, `password`, `salt`, `phone`, `realname`, `sex`, `age`, `portraitid`, `role`, `create_time`, `update_time`) VALUES
	(0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-12-24 11:50:57'),
	(33, '111111', '46f51e1278187c1635bdcd127e91b77a', '00b00c092afd4e3a9fc04087f8c64f2e', NULL, NULL, NULL, 0, NULL, 'teacher', '2023-12-24 11:17:59', '2023-12-24 11:17:59'),
	(34, '我是学生', '289b406d665c090166030580eda193d7', 'e71127ca1ad44918b9d28f8c71b4b06a', NULL, NULL, NULL, 0, NULL, 'student', '2023-12-24 11:18:38', '2023-12-24 11:18:38'),
	(35, '23333', 'fbf06e45eb9ee65b57e349e8a170f3de', 'dbc2918f6d934ddea07e59c197fab3ed', '12124213', '王老五', '男', 24, NULL, 'student', '2023-12-24 11:21:41', '2023-12-24 11:51:31'),
	(36, 'teacher', '3ef988c6e2d1959ac34f804573b3bef2', 'd6f3a3a09ce5483c90e303696fc64ecc', '124123512351', '王二狗', '男', 112412, NULL, 'teacher', '2023-12-24 11:52:17', '2023-12-24 11:52:36'),
	(37, 'student', 'a81d782eb4ee6210e8d47feda94b17be', '98c094c071c642c9bc4af7860e67625f', '13141513161531', 'seg', '男', 20, NULL, 'student', '2023-12-25 10:49:22', '2023-12-25 10:49:44'),
	(38, 'student2', 'cce6be45db95978045e19dcc0c1e047d', '88a8191dd64f4e09ba3c167017b52b4c', NULL, 'aeefegea', NULL, 0, NULL, 'student', '2023-12-25 11:46:38', '2023-12-25 11:46:38');

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

-- 正在导出表  ts1.user_role 的数据：~6 rows (大约)
DELETE FROM `user_role`;
INSERT INTO `user_role` (`uid`, `sno`, `tno`) VALUES
	(33, NULL, 10),
	(34, 4, NULL),
	(35, 5, NULL),
	(36, NULL, 11),
	(37, 6, NULL),
	(38, 7, NULL);

-- 导出  视图 ts1.course_view 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `course_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `course_view` AS select `course`.`cno` AS `cno`,`course`.`cname` AS `cname`,`course`.`dept` AS `dept`,`user`.`realname` AS `tname`,`course`.`tno` AS `tno`,`course`.`day` AS `day`,`course`.`start` AS `start`,`course`.`end` AS `end`,`course`.`room` AS `room`,`course`.`note` AS `note`,`course`.`capacity` AS `capacity`,`course_stunumber`(`course`.`cno`) AS `stunumber`,`course`.`progress` AS `progress` from (((`course` join `teacher` on((`course`.`tno` = `teacher`.`tno`))) join `user_role` on((`course`.`tno` = `user_role`.`tno`))) join `user` on((`user_role`.`uid` = `user`.`uid`)));

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
