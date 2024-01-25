package com.enndfp.shortlink.admin.common.convention.exception;

import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.utils.ResultUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author Enndfp
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("methodArgumentNotValidExceptionHandler: ", ex);
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorMap = getErrors(bindingResult);
        return ResultUtil.failure(ErrorCode.SERVICE_ERROR, errorMap);
    }

    /**
     * 拦截自定义抛出的异常
     */
    @ExceptionHandler(AbstractException.class)
    public Result<?> abstractException(AbstractException ex) {
        log.error("abstractException: " + ex.getMessage(), ex);
        return ResultUtil.failure(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * 运行时异常捕获
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException ex) {
        log.error("runtimeException: ", ex);
        return ResultUtil.failure(ErrorCode.SERVICE_ERROR, ex.getMessage());
    }

    /**
     * 从方法参数校验异常中获取详细异常信息
     *
     * @param bindingResult
     * @return
     */
    private Map<String, String> getErrors(BindingResult bindingResult) {

        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errorList = bindingResult.getFieldErrors();

        for (FieldError error : errorList) {
            // 错误所对应的属性字段名
            String field = error.getField();
            // 错误所对应的信息
            String message = error.getDefaultMessage();
            errorMap.put(field, message);
        }
        return errorMap;
    }
}