package com.ssh.exceptions;


import com.ssh.response.ApiResponse;
import com.ssh.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleNotFoundException(NotFoundException exception) {
        return ResponseUtil.failure(null, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleServiceException(ServiceException exception) {
        return ResponseUtil.failure(null, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
