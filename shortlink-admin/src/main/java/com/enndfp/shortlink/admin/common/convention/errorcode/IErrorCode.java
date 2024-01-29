package com.enndfp.shortlink.admin.common.convention.errorcode;

/**
 * 平台错误码
 *
 * @author Enndfp
 */
public interface IErrorCode {

    /**
     * 错误码
     *
     * @return 错误码
     */
    String code();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String message();
}