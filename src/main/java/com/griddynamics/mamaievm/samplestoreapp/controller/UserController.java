package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.domain.UserLoginResponse;
import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;
import com.griddynamics.mamaievm.samplestoreapp.exception.WrongCredentialsException;
import com.griddynamics.mamaievm.samplestoreapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    public static final String BASE_URL = "/api/v1/users";
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@RequestBody @NonNull @Valid UserDto userDTO) {
        if(userDTO.getId() != null) {
            throw new IllegalArgumentException("Please remove user id from create user request body");
        }
        try {
            return userService.saveUser(userDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    UserDto updateUser(@RequestBody @NonNull @Valid UserDto userDTO) {
        if(userDTO.getId() == null) {
            throw new IllegalArgumentException("User id is required for user update");
        }
        return userService.saveUser(userDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    UserLoginResponse login(@RequestBody @NonNull @Valid UserDto userDTO, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
        
        return new UserLoginResponse(RequestContextHolder.currentRequestAttributes().getSessionId());
    }
    
}
