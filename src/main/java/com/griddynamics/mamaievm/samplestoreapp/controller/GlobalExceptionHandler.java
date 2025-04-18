package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.exception.WrongCredentialsException;
import com.griddynamics.mamaievm.samplestoreapp.service.LoginAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final LoginAttemptService loginAttemptService;
    
    private final HttpServletRequest request;
    
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(WrongCredentialsException.class)
        public void handleException() {
            final String xfHeader = request.getHeader("X-Forwarded-For");
            if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
                loginAttemptService.loginFailed(request.getRemoteAddr());
            } else {
                loginAttemptService.loginFailed(xfHeader.split(",")[0]);
            }
        }
    
}
