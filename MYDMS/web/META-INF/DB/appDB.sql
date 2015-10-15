/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : appDB

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-10-15 09:16:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Dmsfile`
-- ----------------------------
DROP TABLE IF EXISTS `Dmsfile`;
CREATE TABLE `Dmsfile` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `File_Name` varchar(500) DEFAULT NULL,
  `File_Type` varchar(100) DEFAULT NULL,
  `size` varchar(100) DEFAULT NULL,
  `Folder_Id` int(10) DEFAULT NULL COMMENT '1	image/jpeg	HS result.jpg	60	9ca05822-58bc-4173-a956-dc708b1ca82f	1	44648		N	2015-08-10 14:47:56	1	VB\r\n',
  `dode` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Dmsfile
-- ----------------------------
INSERT INTO `Dmsfile` VALUES ('2', 'index.png', 'image/png', '1', '1', '2015-10-15 08:51:44');
INSERT INTO `Dmsfile` VALUES ('3', 'index.png', 'image/png', '1', '1', '2015-10-15 08:52:53');
INSERT INTO `Dmsfile` VALUES ('4', 'index.png', 'image/png', '1', '1', '2015-10-15 08:55:24');
INSERT INTO `Dmsfile` VALUES ('5', '1444879564864.jpeg', 'image/jpeg', '2', '1', '2015-10-15 08:56:11');
INSERT INTO `Dmsfile` VALUES ('6', '1444879707891.jpeg', 'image/jpeg', '2', '1', '2015-10-15 08:58:28');
