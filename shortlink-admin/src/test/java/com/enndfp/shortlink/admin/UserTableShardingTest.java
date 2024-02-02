package com.enndfp.shortlink.admin;

/**
 * @author Enndfp
 */
public class UserTableShardingTest {

    public static final String SQL = "CREATE TABLE `t_group_%d` (\n" +
            "\t`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "\t`gid` VARCHAR ( 32 ) NOT NULL COMMENT '分组标识',\n" +
            "\t`group_name` VARCHAR ( 64 ) NOT NULL COMMENT '分组名称',\n" +
            "\t`username` VARCHAR ( 256 ) NOT NULL COMMENT '创建分组用户名',\n" +
            "\t`sort_order` INT ( 3 ) DEFAULT 0 NOT NULL COMMENT '分组排序',\n" +
            "\t`created_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',\n" +
            "\t`updated_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "\t`is_deleted` TINYINT ( 1 ) DEFAULT 0 NOT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "\tPRIMARY KEY ( `id` ),\n" +
            "\tUNIQUE KEY `idx_unique_gid` ( `gid` ) USING BTREE \n" +
            ") ENGINE = INNODB DEFAULT CHARSET = utf8mb4;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}