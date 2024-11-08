package com.ttn.demo.global.util;

import org.springframework.http.ResponseEntity;
import com.ttn.demo.global.error.ErrorResponse;

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
}
