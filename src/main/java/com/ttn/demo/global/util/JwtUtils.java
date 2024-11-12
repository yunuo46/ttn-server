package com.ttn.demo.global.util;

import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public static void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        String cookieHeader = String.format("refreshToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=None",
                refreshToken,
                5 * 24 * 60 * 60);
        response.addHeader("Set-Cookie", cookieHeader);
    }

    public static TokenResponse setJwtResponse(HttpServletResponse response, Tokens tokenDto) {
        TokenResponse tokenResponseDto = TokenResponse.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .accessTokenExpiresIn(tokenDto.getAccessTokenExpiresIn())
                .build();

        setRefreshTokenInCookie(response, tokenDto.getRefreshToken());
        return tokenResponseDto;
    }
}
