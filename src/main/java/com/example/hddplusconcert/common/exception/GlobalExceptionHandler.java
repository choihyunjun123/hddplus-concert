package com.example.hddplusconcert.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getErrorCode().getMessage());

        return ResponseEntity.ok(body);
    }
}
