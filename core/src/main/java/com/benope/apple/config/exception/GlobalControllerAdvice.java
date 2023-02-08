package com.benope.apple.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ConditionalOnWebApplication
@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public void handleBusinessException(BusinessException e) {
        log.error("", e);
        throw e;
    }

}
