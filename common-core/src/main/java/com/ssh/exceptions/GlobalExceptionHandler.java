package com.ssh.exceptions;


import com.ssh.response.ApiResponse;
import com.ssh.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
