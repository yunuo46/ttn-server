package com.ttn.demo.domain.letter.exception;

import com.ttn.demo.global.error.ErrorCode;
import com.ttn.demo.global.error.exception.BusinessException;

public class SenderNotFoundException extends BusinessException {
    public SenderNotFoundException() {
        super(ErrorCode.SENDER_NOT_FOUND);
    }
}
