package com.griddynamics.mamaievm.samplestoreapp.dto;

import jakarta.validation.constraints.Email;
import java.io.Serializable;

import lombok.Value;

/**
 * DTO for {@link com.griddynamics.mamaievm.samplestoreapp.entity.User}
 */
@Value
public class UserDto implements Serializable {

    Long id;
    @Email(message = "Incorrect email")
    String email;
    String password;

}