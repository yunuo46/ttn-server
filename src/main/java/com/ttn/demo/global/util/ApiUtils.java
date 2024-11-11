package com.ttn.demo.global.util;

import org.springframework.http.ResponseEntity;
import com.ttn.demo.global.error.ErrorResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ApiUtils {
    private ApiUtils() {}

    public static <T> ApiResponse<T> success(T response) {
        return ApiResponse.create(true, response, null);
    }

    public static ResponseEntity<?> error(ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(ApiResponse.create(false, null, errorResponse));
    }

    public static Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return Long.valueOf(userDetails.getUsername());
        } else {
            throw new IllegalStateException("Authentication information is missing or invalid.");
        }
    }
}
