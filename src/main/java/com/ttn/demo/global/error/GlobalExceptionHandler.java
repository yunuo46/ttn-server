package com.ttn.demo.global.error;

import com.ttn.demo.global.error.exception.BusinessException;
import com.ttn.demo.global.util.ApiUtils;
import com.ttn.demo.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<?> handleBusinessException(final BusinessException e) {
        return handleException(e, e.getErrorCode());
    }

    private ResponseEntity<?> handleException(Exception e, ErrorCode errorCode) {
        log.error("Exception occurred: ",e);
        return ApiUtils.error(ErrorResponse.of(errorCode));
    }
}
