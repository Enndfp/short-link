package com.enndfp.shortlink.admin.common.convention.errorcode;

/**
 * 基础错误码定义
 *
 * @author Enndfp
 */
public enum ErrorCode implements IErrorCode {

    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "用户端错误"),

    // ========== 二级宏观错误码 用户相关错误 ==========
    USER_TOKEN_FAIL("A000100", "用户Token验证失败"),




    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "服务端错误"),

    // ========== 二级宏观错误码 用户相关错误 ==========
    USER_NULL("B000100", "用户记录不存在"),
    USER_NAME_EXIST("B000101", "用户名已存在"),
    USER_EXIST("B000102", "用户记录已存在"),
    USER_SAVE_ERROR("B000103", "用户记录新增失败"),


    // ========== 一级宏观错误码 调用第三方服务出错 ==========
    REMOTE_ERROR("C000001", "调用第三方服务出错");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}