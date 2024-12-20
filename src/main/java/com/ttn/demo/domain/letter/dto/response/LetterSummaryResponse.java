package com.ttn.demo.domain.letter.dto.response;

import com.ttn.demo.domain.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class LetterSummaryResponse {
    private Long id;
    private String title;
    private String senderName;
    private String receiverName;
    private LocalDateTime sendedAt;

    public static LetterSummaryResponse of(Letter letter){
        return new LetterSummaryResponse.LetterSummaryResponseBuilder()
                .id(letter.getId())
                .senderName(letter.getSender().getNickname())
                .receiverName(letter.getReceiver().getNickname())
                .title(letter.getTitle())
                .sendedAt(letter.getSendedAt())
                .build();
    }
}
