/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80402 (8.4.2)
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80402 (8.4.2)
 File Encoding         : 65001

 Date: 19/11/2024 16:11:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
  `creator_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, '/user/select', 'Permission to view user list', 1, 1, '2024-11-19 06:01:01', '2024-11-19 06:01:52');
INSERT INTO `t_permission` VALUES (2, '/user/update', 'Permission to edit user details', 1, 1, '2024-11-19 06:01:01', '2024-11-19 06:02:15');
INSERT INTO `t_permission` VALUES (3, '/user/delete', 'Permission to delete users', 1, 1, '2024-11-19 06:01:01', '2024-11-19 07:45:14');
INSERT INTO `t_permission` VALUES (4, '/user/insert', 'Permission to insert users', 1, 1, '2024-11-19 06:01:01', '2024-11-19 06:03:03');
INSERT INTO `t_permission` VALUES (5, '/user/*', 'Permission to anything for users', 1, 1, '2024-11-19 06:01:01', '2024-11-19 06:03:16');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `creator_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin', 'System administrator with full access', 1, 1, '2024-11-19 05:59:46', '2024-11-19 06:00:24');
INSERT INTO `t_role` VALUES (2, 'manager', 'Manager with limited administrative rights', 1, 1, '2024-11-19 05:59:46', '2024-11-19 06:00:30');
INSERT INTO `t_role` VALUES (3, 'user', 'Regular user with basic access', 1, 1, '2024-11-19 05:59:46', '2024-11-19 06:00:36');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `permission_id` int NOT NULL COMMENT '权限ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_role_permission`(`role_id` ASC, `permission_id` ASC) USING BTREE,
  INDEX `fk_role_permission_permission`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `fk_role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (1, 1, 1, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (2, 1, 2, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (3, 1, 3, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (4, 2, 1, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (5, 2, 4, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (6, 3, 1, '2024-11-19 06:03:46');
INSERT INTO `t_role_permission` VALUES (7, 1, 5, '2024-11-19 07:45:45');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` int NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `creator_id` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zhangsan', '123', 0, '12345@qq.com', '18395899237', 1, 1, '2024-10-10 07:03:34', '2024-11-19 03:12:25');
INSERT INTO `t_user` VALUES (2, 'lisi', '123', 1, '12345@qq.com', '18395899237', 2, 2, '2024-11-19 03:38:16', '2024-11-19 03:38:24');
INSERT INTO `t_user` VALUES (3, 'janedoe', 'password123', 2, 'jane@example.com', '12345678903', 1, 1, '2024-11-19 05:59:22', '2024-11-19 05:59:36');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1, 1, '2024-11-19 06:03:29');
INSERT INTO `t_user_role` VALUES (2, 2, 2, '2024-11-19 06:03:29');
INSERT INTO `t_user_role` VALUES (3, 3, 3, '2024-11-19 06:03:29');

SET FOREIGN_KEY_CHECKS = 1;
