package com.enndfp.shortlink.admin.common.convention.errorcode;

/**
 * 错误码定义
 *
 * @author Enndfp
 */
public enum ErrorCode implements IErrorCode {

    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "客户端请求错误"),

    // ========== 二级宏观错误码 ==========
    USER_NAME_NULL("A000100", "用户名为空"),
    PASSWORD_NULL("A000101", "密码为空"),
    GROUP_NAME_NULL("A000201", "分组名称为空"),


    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "服务端错误"),

    // ========== 二级宏观错误码 ==========
    USER_NOT_EXIST("B000100", "用户不存在"),
    USER_NAME_EXIST("B000101", "用户名已存在"),
    USER_REGISTER_ERROR("B000102", "用户保存失败"),
    USER_INFO_UPDATE_ERROR("B000103", "用户信息更新失败"),
    USER_HAS_LOGIN("B000104", "用户已登录"),
    USER_OR_TOKEN_NULL("B000105", "用户不存在或token已过期"),
    USER_NOT_LOGIN("B000106", "用户未登录"),
    GROUP_SAVE_ERROR("B000201", "分组保存失败"),
    GROUP_UPDATE_ERROR("B000202", "分组修改失败"),


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