-- create db
CREATE DATABASE IF NOT EXISTS ssm_swagger CHARACTER SET utf8mb4;
USE ssm_swagger;

-- create table user
DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  `id` char(32) NOT NULL COMMENT '用户id',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `lastlogin` timestamp DEFAULT current_timestamp COMMENT '最近登录时间',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- init data user
INSERT INTO user(id, password, lastlogin, username) VALUES
  ((replace(uuid(), '-', '')), md5('123456'), NULL, 'test'),
  ((replace(uuid(), '-', '')), md5('123456'), NULL, 'test2')

