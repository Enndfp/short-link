package com.enndfp.shortlink.project.common.convention.errorcode;

/**
 * 错误码定义
 *
 * @author Enndfp
 */
public enum ErrorCode implements IErrorCode {

    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "客户端请求错误"),

    // ========== 二级宏观错误码 ==========
    ORIGIN_URL_NULL("A000300", "原始链接为空"),



    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "服务端错误"),

    // ========== 二级宏观错误码 ==========
    LINK_CREATE_ERROR("B000300", "短链接创建失败"),
    LINK_FREQUENT_CREATE("B000301", "短链接频繁生成，请稍后再试"),



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