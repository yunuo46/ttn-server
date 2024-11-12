package com.ttn.demo.domain.user.application;

import com.ttn.demo.domain.user.dao.RefreshTokenRepository;
import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.util.TokenGenerator;
import com.ttn.demo.domain.user.util.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenReissueService {

    private final TokenProvider tokenProvider;
    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Tokens reissue(String refreshToken) {
        Long userId = tokenProvider.getUserIdFromToken(refreshToken);
        System.out.println(userId);
        return tokenGenerator.generate(userId);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
