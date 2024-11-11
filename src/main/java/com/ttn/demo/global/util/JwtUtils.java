package com.ttn.demo.global.util;

import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public static void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");  // 쿠키가 유효한 경로 설정
        cookie.setMaxAge(5 * 24 * 60 * 60);
        response.addCookie(cookie);
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
