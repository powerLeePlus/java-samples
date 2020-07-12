CREATE DATABASE IF NOT EXISTS `shiro_demo` character set utf8;

use shiro_demo;

-- ----------------------------
-- Table structure for shiro_user
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user`;
CREATE TABLE `shiro_user` (
                          `id` int(6) NOT NULL AUTO_INCREMENT,
                          `username` varchar(40) DEFAULT NULL,
                          `password` varchar(40) DEFAULT NULL,
                          `salt` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shiro_perms
-- ----------------------------
DROP TABLE IF EXISTS `shiro_perms`;
CREATE TABLE `shiro_perms` (
                          `id` int(6) NOT NULL AUTO_INCREMENT,
                          `name` varchar(80) DEFAULT NULL,
                          `url` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shiro_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role`;
CREATE TABLE `shiro_role` (
                          `id` int(6) NOT NULL AUTO_INCREMENT,
                          `name` varchar(60) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shiro_role_perms
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role_perms`;
CREATE TABLE `shiro_role_perms` (
                                `id` int(6) NOT NULL,
                                `roleid` int(6) DEFAULT NULL,
                                `permsid` int(6) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shiro_user_role
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user_role`;
CREATE TABLE `shiro_user_role` (
                               `id` int(6) NOT NULL,
                               `userid` int(6) DEFAULT NULL,
                               `roleid` int(6) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



