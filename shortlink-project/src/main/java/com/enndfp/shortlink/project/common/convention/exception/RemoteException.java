package com.enndfp.shortlink.project.common.convention.exception;

import com.enndfp.shortlink.project.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.project.common.convention.errorcode.IErrorCode;

/**
 * 远程服务调用异常
 *
 * @author Enndfp
 */
public class RemoteException extends AbstractException {

    public RemoteException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public RemoteException(IErrorCode errorCode,String message) {
        this(message, null, errorCode);
    }
    public RemoteException(String message) {
        this(message, null, ErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}