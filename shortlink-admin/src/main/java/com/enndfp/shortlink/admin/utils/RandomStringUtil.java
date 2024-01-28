package com.enndfp.shortlink.admin.utils;

import java.security.SecureRandom;

/**
 * 生成随机字符串工具类
 *
 * @author Enndfp
 */
public class RandomStringUtil {

    // 定义小写字母字符串
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    // 将小写字母字符串转换为大写
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    // 定义数字字符串
    private static final String NUMBER = "0123456789";

    // 将小写字母、大写字母和数字组合成一个用于生成随机字符串的数据源
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    // 使用SecureRandom来确保生成的随机数更安全
    private static final SecureRandom random = new SecureRandom();

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 生成一个随机索引，用于从数据源字符串中选择字符
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            // 将选择的字符添加到字符串构建器中
            sb.append(rndChar);
        }

        return sb.toString();
    }
}
