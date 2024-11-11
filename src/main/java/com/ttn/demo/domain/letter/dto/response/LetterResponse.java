package com.ttn.demo.domain.letter.dto.response;

import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class LetterResponse {
    private String title;
    private String contents;
    private String senderName;
    private String receiverName;
    private String language;
    private LocalDateTime sendedAt;
    private LocalDateTime openedAt;

    public static LetterResponse of(Letter letter){
        return new LetterResponseBuilder()
                .senderName(letter.getSender().getNickname())
                .receiverName(letter.getReceiver().getNickname())
                .title(letter.getTitle())
                .contents(letter.getContents())
                .language(letter.getLanguage())
                .sendedAt(letter.getSendedAt())
                .openedAt(letter.getOpenedAt())
                .build();
    }
}
