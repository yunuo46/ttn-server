package com.ttn.demo.domain.letter.exception;

import com.ttn.demo.global.error.ErrorCode;
import com.ttn.demo.global.error.exception.BusinessException;

public class LetterNotFoundException extends BusinessException {
    public LetterNotFoundException(Long id) {
        super(ErrorCode.LETTER_NOT_FOUND);
    }
}
