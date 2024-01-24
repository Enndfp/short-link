package com.enndfp.shortlink.admin.common.convention.exception;

import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.errorcode.IErrorCode;

import java.util.Optional;

/**
 * 服务端异常
 *
 * @author Enndfp
 */
public class ServerException extends AbstractException {

    public ServerException(String message) {
        this(message, null, ErrorCode.SERVICE_ERROR);
    }

    public ServerException(IErrorCode errorCode,String message) {
        this(message, null, errorCode);
    }

    public ServerException(IErrorCode errorCode) {
        this(null, errorCode);
    }

    public ServerException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServerException(String message, Throwable throwable, IErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}