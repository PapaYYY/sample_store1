package com.griddynamics.mamaievm.samplestoreapp.controller;

import com.griddynamics.mamaievm.samplestoreapp.domain.UserLoginResponse;
import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;
import com.griddynamics.mamaievm.samplestoreapp.exception.BruteForcingBlockerException;
import com.griddynamics.mamaievm.samplestoreapp.service.LoginAttemptService;
import com.griddynamics.mamaievm.samplestoreapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    public static final String BASE_URL = "/api/v1/users";
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final LoginAttemptService loginAttemptService;
    
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
    UserDto updateUser(@RequestBody @NonNull @Valid UserDto userDTO, @AuthenticationPrincipal UserDetails userDetails) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = userDetails.getUsername();
        log.debug("Updating user {}", currentUserName);
        if (!currentUserName.equals(userDTO.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Please provide proper user email");
        if(userDTO.getId() == null) {
            throw new IllegalArgumentException("User id is required for user update");
        }
        return userService.saveUser(userDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    String getCurrentUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    UserLoginResponse login(@RequestBody @NonNull @Valid UserDto userDTO, HttpServletRequest request, HttpServletResponse response) {
        if (loginAttemptService.isBlocked()) {
            throw new BruteForcingBlockerException("IP is blocked");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
        
        return new UserLoginResponse(RequestContextHolder.currentRequestAttributes().getSessionId());
    }
    
}
