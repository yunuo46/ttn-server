package com.ttn.demo.domain.letter.exception;

import com.ttn.demo.global.error.ErrorCode;
import com.ttn.demo.global.error.exception.BusinessException;

public class NoAvailableReceiverException extends BusinessException {
    public NoAvailableReceiverException() {
        super(ErrorCode.RECEIVER_NOT_EXISTED);
    }
}
