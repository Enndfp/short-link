package com.enndfp.shortlink.admin.common.convention.result;

import com.enndfp.shortlink.admin.common.convention.errorcode.IErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 全局返回对象
 *
 * @author Enndfp
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    public Result(String code, String message, T data, String requestId) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.requestId = requestId;
    }

    public Result(IErrorCode iErrorCode) {
        this(iErrorCode.code(), iErrorCode.message(), null, null);
    }
}