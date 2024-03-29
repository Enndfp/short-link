package com.enndfp.shortlink.admin.utils;


import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.exception.AbstractException;
import com.enndfp.shortlink.admin.common.convention.exception.ClientException;
import com.enndfp.shortlink.admin.common.convention.exception.RemoteException;
import com.enndfp.shortlink.admin.common.convention.exception.ServerException;

/**
 * 抛异常工具类
 *
 * @author Enndfp
 */
public class ThrowUtil {

    /**
     * 无条件抛出ClientException
     *
     * @param errorCode 业务错误码
     */
    public static void throwClientException(ErrorCode errorCode) {
        throw new ClientException(errorCode);
    }

    /**
     * 无条件抛出RemoteException
     *
     * @param errorCode 业务错误码
     */
    public static void throwRemoteException(ErrorCode errorCode) {
        throw new RemoteException(errorCode);
    }

    /**
     * 无条件抛出ServerException
     *
     * @param errorCode 业务错误码
     */
    public static void throwServerException(ErrorCode errorCode) {
        throw new ServerException(errorCode);
    }

    /**
     * 条件成立则抛出指定的异常
     *
     * @param condition 条件
     * @param exception 要抛出的异常
     */
    public static void throwIf(boolean condition, AbstractException exception) {
        if (condition) {
            throw exception;
        }
    }

    /**
     * 条件成立则抛出ClientException
     *
     * @param condition 条件
     * @param errorCode 业务错误码
     */
    public static void throwClientIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new ClientException(errorCode, errorCode.message()));
    }

    /**
     * 条件成立则抛出RemoteException
     *
     * @param condition 条件
     * @param errorCode 业务错误码
     */
    public static void throwRemoteIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new RemoteException(errorCode, errorCode.message()));
    }

    /**
     * 条件成立则抛出ServerException
     *
     * @param condition 条件
     * @param errorCode 业务错误码
     */
    public static void throwServerIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new ServerException(errorCode, errorCode.message()));
    }
}

