package com.ttn.demo.domain.buddy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BuddyLetterRequest {
    private String buddyName;
    private String title;
    private String contents;
}
