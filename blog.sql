DROP DATABASE IF EXISTS `blog`;
CREATE DATABASE `blog` DEFAULT character set utf8mb4;
SET names utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `blog`;

CREATE TABLE `tb_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT 'data_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tb_tag';

insert into `tb_tag` values (null,"mysql"),(null,"redis");