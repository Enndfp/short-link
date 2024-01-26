package com.enndfp.shortlink.admin;

/**
 * @author Enndfp
 */
public class UserTableShardingTest {

    public static final String SQL = "CREATE TABLE `t_user_%d` (\n" +
            "\t`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "\t`username` VARCHAR ( 256 ) NOT NULL COMMENT '用户名',\n" +
            "\t`password` VARCHAR ( 512 ) NOT NULL COMMENT '密码',\n" +
            "\t`real_name` VARCHAR ( 256 ) DEFAULT NULL COMMENT '真实姓名',\n" +
            "\t`phone` VARCHAR ( 128 ) DEFAULT NULL COMMENT '手机号',\n" +
            "\t`mail` VARCHAR ( 512 ) DEFAULT NULL COMMENT '邮箱',\n" +
            "\t`deleted_time` datetime DEFAULT '2000-01-01 00:00:01' NOT NULL COMMENT '注销时间戳',\n" +
            "\t`created_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',\n" +
            "\t`updated_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "\t`is_deleted` TINYINT ( 1 ) DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "\tPRIMARY KEY ( `id` ),\n" +
            "\tUNIQUE KEY `idx_unique_username` ( `username` ) USING BTREE \n" +
            ") ENGINE = INNODB DEFAULT CHARSET = utf8mb4;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}