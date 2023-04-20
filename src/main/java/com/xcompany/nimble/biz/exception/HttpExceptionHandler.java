package com.xcompany.nimble.biz.exception;

import com.xcompany.nimble.biz.common.BizResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class HttpExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ResponseEntity<BizResponse<String>> handleBizException(BizException e, HttpServletRequest request) {
        log.error("business exception occurred when request to {}.", request.getServletPath(), e);
        return ResponseEntity.ok(BizResponse.fail(e.getBizErrorCode().getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BizResponse<String>> handleThrowable(Throwable t, HttpServletRequest request) {
        log.error("error occurred when request to {}.", request.getServletPath(), t);
        return ResponseEntity.ok(BizResponse.fail(BizErrorCode.UNKNOWN_ERROR.getErrorCode(), t.getMessage()));
    }
}
