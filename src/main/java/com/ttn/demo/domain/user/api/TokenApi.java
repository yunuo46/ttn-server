package com.ttn.demo.domain.user.api;

import com.ttn.demo.domain.user.application.TokenReissueService;
import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.dto.response.TokenResponse;
import com.ttn.demo.global.util.ApiResponse;
import com.ttn.demo.global.util.ApiUtils;
import com.ttn.demo.global.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class TokenApi {

    private final TokenReissueService tokenReissueService;

    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> accessTokenReissue(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 리프레시 토큰 읽기
        String refreshToken = tokenReissueService.getRefreshTokenFromCookies(request);
        Tokens tokens = tokenReissueService.reissue(refreshToken);
        TokenResponse tokenResponseDto = JwtUtils.setJwtResponse(response,tokens);
        return ApiUtils.success(tokenResponseDto);
    }

}
