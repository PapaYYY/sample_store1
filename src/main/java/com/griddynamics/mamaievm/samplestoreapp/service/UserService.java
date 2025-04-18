package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;

public interface UserService {

    UserDto saveUser(UserDto userDTO);
    
    boolean userLogin(UserDto userDTO);
}
