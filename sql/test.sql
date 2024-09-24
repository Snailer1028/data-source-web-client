-- test.t_test_user definition

CREATE TABLE `t_test_user`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
    `gender`      int                                                           DEFAULT NULL COMMENT '性别',
    `email`       varchar(255)                                                  DEFAULT NULL COMMENT '电子邮件',
    `phone`       char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '手机号',
    `owner_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所有者名称',
    `creator_id`  bigint                                                        DEFAULT NULL COMMENT '创建者ID',
    `updater_id`  bigint                                                        DEFAULT NULL COMMENT '更新者ID',
    `create_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     tinyint                                                       DEFAULT '0' COMMENT '是否删除标记',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';
