package com.ttn.demo.domain.letter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LetterSummaryResponse {
    private String title;
    private String senderName;
    private String receiverName;
}
