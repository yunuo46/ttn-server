package com.ttn.demo.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String nickname;
    private String password;
    private String language;
}
