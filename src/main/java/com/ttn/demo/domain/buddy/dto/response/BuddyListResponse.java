package com.ttn.demo.domain.buddy.dto.response;

import com.ttn.demo.domain.buddy.domain.Buddy;
import lombok.Getter;

@Getter
public class BuddyListResponse {
    private final String buddyName;

    public BuddyListResponse(String nickname) {
        this.buddyName = nickname;
    }

    public static BuddyListResponse from(Buddy buddy) {
        return new BuddyListResponse(buddy.getReceiver().getNickname());
    }
}
