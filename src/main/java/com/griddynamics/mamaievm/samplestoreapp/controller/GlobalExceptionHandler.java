package com.griddynamics.mamaievm.samplestoreapp.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.griddynamics.mamaievm.samplestoreapp.service.LoginAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final LoginAttemptService loginAttemptService;
    
    private final HttpServletRequest request;
    
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<String> handleException(BadCredentialsException bce) {
            final String xfHeader = request.getHeader("X-Forwarded-For");
            if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
                loginAttemptService.loginFailed(request.getRemoteAddr());
            } else {
                loginAttemptService.loginFailed(xfHeader.split(",")[0]);
            }
            return new ResponseEntity<>(bce.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleResourceNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(ex.getMessage());
    }
    
}
