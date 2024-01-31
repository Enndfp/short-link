package com.enndfp.shortlink.project;

/**
 * @author Enndfp
 */
public class LinkTableShardingTest {

    public static final String SQL = "CREATE TABLE `t_link_%d`  (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '域名',\n" +
            "  `short_uri` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '短链接',\n" +
            "  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '完整短链接',\n" +
            "  `origin_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始链接',\n" +
            "  `click_num` int NULL DEFAULT 0 COMMENT '点击量',\n" +
            "  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "  `favicon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站图标',\n" +
            "  `enable_status` tinyint(1) NULL DEFAULT 1 COMMENT '启用标识 0：未启用 1：已启用',\n" +
            "  `created_type` tinyint(1) NULL DEFAULT NULL COMMENT '创建类型 0：控制台 1：接口',\n" +
            "  `valid_date_type` tinyint(1) NULL DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：用户自定义',\n" +
            "  `valid_date` datetime NULL DEFAULT NULL COMMENT '有效期',\n" +
            "  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',\n" +
            "  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  UNIQUE INDEX `idx_unique_full_short_url`(`full_short_url` ASC) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((SQL) + "%n", i);
        }
    }
}