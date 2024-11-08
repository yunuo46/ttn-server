package com.ttn.demo.domain.user.api;

import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.dto.response.UserResponse;
import com.ttn.demo.domain.user.application.UserService;
import com.ttn.demo.global.util.ApiResponse;
import com.ttn.demo.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(@RequestBody User user) {
        UserResponse savedUser = userService.createUser(user);
        return ApiUtils.success(savedUser);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ApiUtils.success(user);
    }

    @GetMapping
    public ApiResponse<Iterable<UserResponse>> getAllUsers() {
        Iterable<UserResponse> users = userService.getAllUsers();
        return ApiUtils.success(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiUtils.success(null);
    }
}
