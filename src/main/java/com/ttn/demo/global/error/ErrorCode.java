package com.ttn.demo.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"User not found"),

    // Letter
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND,"Letter not found"),
    RECEIVER_NOT_EXISTED(HttpStatus.NOT_FOUND,"No users available for receiver"),
    SENDER_NOT_FOUND(HttpStatus.NOT_FOUND,"Sender not found");

    private final HttpStatus status;
    private final String message;
}
