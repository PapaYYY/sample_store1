package com.griddynamics.mamaievm.samplestoreapp.service;

import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;
import com.griddynamics.mamaievm.samplestoreapp.entity.User;
import com.griddynamics.mamaievm.samplestoreapp.exception.BruteForcingBlockerException;
import com.griddynamics.mamaievm.samplestoreapp.mapper.UserMapper;
import com.griddynamics.mamaievm.samplestoreapp.repository.UserRepository;
import com.griddynamics.mamaievm.samplestoreapp.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;
    
    @Override
    public UserDto saveUser(UserDto userDTO) {
        if(userDTO.getId() == null) {
            User user = userMapper.toEntity(userDTO);
            if(userRepository.existsByEmail(user.getEmail())) throw new IllegalArgumentException("User with id " + user.getId() + " already exists");
            return userMapper.toDto(userRepository.save(user));
        } else {
            User user = userRepository.findById(userDTO.getId()).orElseThrow();
            User updatedUser = userMapper.partialUpdate(userDTO, user);
            return userMapper.toDto(userRepository.save(updatedUser));
        }
    }

    @Override
    public boolean userLogin(UserDto userDTO) {
        if (loginAttemptService.isBlocked()) {
            throw new BruteForcingBlockerException("IP is blocked");
        }
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow();
        return PasswordUtils.verifyPassword(userDTO.getPassword(), user.getHashedPassword());
    }

}



