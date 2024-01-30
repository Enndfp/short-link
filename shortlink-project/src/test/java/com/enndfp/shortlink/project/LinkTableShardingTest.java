package com.enndfp.shortlink.project;

/**
 * @author Enndfp
 */
public class LinkTableShardingTest {

    public static final String SQL = "CREATE TABLE `t_link_%d` (\n" +
            "\t`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "\t`domain` VARCHAR ( 128 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '域名',\n" +
            "\t`short_uri` VARCHAR ( 8 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '短链接',\n" +
            "\t`full_short_url` VARCHAR ( 128 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '完整短链接',\n" +
            "\t`origin_url` VARCHAR ( 1024 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始链接',\n" +
            "\t`click_num` INT NULL DEFAULT 0 COMMENT '点击量',\n" +
            "\t`gid` VARCHAR ( 32 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "\t`enable_status` TINYINT ( 1 ) NULL DEFAULT 1 COMMENT '启用标识 0：未启用 1：已启用',\n" +
            "\t`created_type` TINYINT ( 1 ) NULL DEFAULT NULL COMMENT '创建类型 0：控制台 1：接口',\n" +
            "\t`valid_date_type` TINYINT ( 1 ) NULL DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：用户自定义',\n" +
            "\t`valid_date` datetime NULL DEFAULT NULL COMMENT '有效期',\n" +
            "\t`description` VARCHAR ( 1024 ) CHARACTER \n" +
            "\tSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',\n" +
            "\t`created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "\t`updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "\t`is_deleted` TINYINT ( 1 ) NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "\tPRIMARY KEY ( `id` ) USING BTREE,\n" +
            "\tUNIQUE INDEX `idx_unique_full_short_url` ( `full_short_url` ASC ) USING BTREE \n" +
            ") ENGINE = INNODB CHARACTER \n" +
            "SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}