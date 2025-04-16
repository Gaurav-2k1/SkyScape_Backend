package com.ssh.utils;

import com.ssh.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success(
            T data,
            String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> failure(
            T data,
            String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>("BAD_REQUEST", status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }
}
