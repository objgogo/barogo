package com.objgogo.barogo.common.handler;

import com.objgogo.barogo.common.exception.BarogoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BarogoExceptionHandler {

    @ExceptionHandler(BarogoException.class)
    public ResponseEntity<Object> handleCustomException(BarogoException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        responseBody.put("code", ex.getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
