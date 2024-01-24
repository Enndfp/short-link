package com.enndfp.shortlink.admin.utils;

import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.exception.AbstractException;
import com.enndfp.shortlink.admin.common.convention.result.Result;

import java.util.Map;
import java.util.Optional;

/**
 * 全局返回对象构造器
 *
 * @author Enndfp
 */
public class ResultUtil {

    /**
     * 构造无返回数据成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>("200", "操作成功！", null, null);
    }

    /**
     * 构造带返回数据的成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>("200", "操作成功！", data, null);
    }

    /**
     * 构建服务端失败响应
     */
    public static Result failure() {
        return new Result<>(ErrorCode.SERVICE_ERROR.code(), ErrorCode.SERVICE_ERROR.message(), null, null);
    }

    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    public static <T> Result<T> failure(AbstractException abstractException) {
        String errorCode = Optional.ofNullable(abstractException.getErrorCode())
                .orElse(ErrorCode.SERVICE_ERROR.code());
        String errorMessage = Optional.ofNullable(abstractException.getErrorMessage())
                .orElse(ErrorCode.SERVICE_ERROR.message());
        return new Result<>(errorCode, errorMessage, null, null);
    }

    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */
    public static Result failure(String errorCode, String errorMessage) {
        return new Result<>(errorCode, errorMessage, null, null);
    }

    /**
     * 通过 errorCode、errorMessage、data 构建失败响应
     */
    public static Result failure(ErrorCode errorCode, Map map) {
        return new Result<>(errorCode.code(), errorCode.message(), map, null);
    }

    /**
     * 通过 errorCode、errorMessage、data 构建失败响应
     */
    public static Result failure(ErrorCode errorCode, String message) {
        return new Result<>(errorCode.code(), message, null, null);
    }
}