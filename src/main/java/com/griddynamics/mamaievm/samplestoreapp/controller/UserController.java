package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.domain.UserLoginResponse;
import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;
import com.griddynamics.mamaievm.samplestoreapp.exception.WrongCredentialsException;
import com.griddynamics.mamaievm.samplestoreapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

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
    UserLoginResponse login(@RequestBody @NonNull @Valid UserDto userDTO, HttpSession session) throws WrongCredentialsException {
        if(userService.userLogin(userDTO)) return new UserLoginResponse(RequestContextHolder.currentRequestAttributes().getSessionId());
        else throw new WrongCredentialsException();
    }
    
}
