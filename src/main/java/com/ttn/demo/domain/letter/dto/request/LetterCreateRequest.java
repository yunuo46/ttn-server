package com.ttn.demo.domain.letter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LetterCreateRequest {
    private Long senderId;
    private String title;
    private String contents;
}
