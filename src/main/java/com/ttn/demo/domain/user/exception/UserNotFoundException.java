package com.ttn.demo.domain.user.exception;

import com.ttn.demo.global.error.ErrorCode;
import com.ttn.demo.global.error.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
