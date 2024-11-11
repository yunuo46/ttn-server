package com.ttn.demo.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
}
