package com.bykeeasy.infrastructure.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        String message = ex.getMessage();
        
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        if ("Credenciales inválidas".equalsIgnoreCase(message)) {
            status = HttpStatus.UNAUTHORIZED;
            response.put("error", "Correo o contraseña incorrectos");
        } else {
            response.put("error", message);
        }
        
        return new ResponseEntity<>(response, status);
    }
}
