package com.medical.interceptor;

import com.medical.model.CommonResponse;
import com.medical.model.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public CommonResponse handleBaseException(BaseException ex) {
        log.error("业务异常信息：{}", ex.getMessage());
        return CommonResponse.fail(ex.getMessage());
    }

    /**
     * 捕获 SQL 约束异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public CommonResponse handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + "用户名重复";
            log.error("SQL 约束异常信息：{}", msg);
            return CommonResponse.fail(msg);
        } else {
            log.error("未知 SQL 异常信息：{}", message);
            return CommonResponse.fail("未知错误");
        }
    }

    /**
     * 捕获所有未处理的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception ex) {
        log.error("未知异常信息：{}", ex.getMessage(), ex);
        return CommonResponse.fail("未知错误");
    }


}
