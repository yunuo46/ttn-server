package com.ttn.demo.domain.user.api;

import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.application.UserService;
import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.dto.request.UserRequest;
import com.ttn.demo.domain.user.dto.response.TokenResponse;
import com.ttn.demo.domain.user.dto.response.UserResponse;
import com.ttn.demo.global.util.ApiResponse;
import com.ttn.demo.global.util.ApiUtils;
import com.ttn.demo.global.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TokenResponse> join(@RequestBody UserRequest userRequest, HttpServletResponse response) {
        Tokens tokens = userService.createUser(userRequest);
        TokenResponse tokenResponseDto = JwtUtils.setJwtResponse(response,tokens);
        return ApiUtils.success(tokenResponseDto);
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody UserRequest userRequest, HttpServletResponse response) {
        Tokens tokens = userService.loginUser(userRequest);
        TokenResponse tokenResponseDto = JwtUtils.setJwtResponse(response,tokens);
        return ApiUtils.success(tokenResponseDto);
    }

    @GetMapping("/all")
    public ApiResponse<Iterable<UserResponse>> getAllUsers() {
        Iterable<UserResponse> users = userService.getAllUsers();
        return ApiUtils.success(users);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteUser() {
        Long id = ApiUtils.getUserIdFromAuthentication();
        userService.deleteUser(id);
        return ApiUtils.success(null);
    }
}
