DROP DATABASE IF EXISTS `jt-sso`;
CREATE DATABASE  `jt-sso` DEFAULT CHARACTER SET utf8mb4;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

use `jt-sso`;


CREATE TABLE `tb_menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限表';

CREATE TABLE `tb_roles` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `tb_role_menus` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(11) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='角色与权限关系表';

CREATE TABLE `tb_users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `status` varchar(10) DEFAULT NULL COMMENT '状态 PROHIBIT：禁用   NORMAL：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='系统用户表';

CREATE TABLE `tb_user_roles` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='用户与角色关系表';

CREATE TABLE `tb_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `createdTime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1',
  `error` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='操作日志';

create table `tb_files`(
   `id`   bigint not null auto_increment comment 'id',
   `name` varchar(50) DEFAULT NULL COMMENT '文件名',
   `url`  varchar(255) comment '文件地址',
    primary key (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文件';

INSERT INTO `tb_menus` VALUES (1, 'select resource', 'sys:res:list');
INSERT INTO `tb_menus` VALUES (2, 'upload resource', 'sys:res:create');
INSERT INTO `tb_menus` VALUES (3, 'delete roles', 'sys:res:delete');

INSERT INTO `tb_roles` VALUES (1, 'ADMIN');
INSERT INTO `tb_roles` VALUES (2, 'USER');

INSERT INTO `tb_role_menus` VALUES (1, 1, 1);
INSERT INTO `tb_role_menus` VALUES (2, 1, 2);
INSERT INTO `tb_role_menus` VALUES (3, 1, 3);
INSERT INTO `tb_role_menus` VALUES (4, 2, 1);

INSERT INTO `tb_users` VALUES (1, 'admin', '$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa', 'NORMAL');
INSERT INTO `tb_users` VALUES (2, 'user', '$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS', 'NORMAL');

INSERT INTO `tb_user_roles` VALUES (1, 1, 1);
INSERT INTO `tb_user_roles` VALUES (2, 2, 2);
