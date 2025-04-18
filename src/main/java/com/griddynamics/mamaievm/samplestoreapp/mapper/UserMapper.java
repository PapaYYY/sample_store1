package com.griddynamics.mamaievm.samplestoreapp.mapper;

import com.griddynamics.mamaievm.samplestoreapp.dto.UserDto;
import com.griddynamics.mamaievm.samplestoreapp.entity.User;
import com.griddynamics.mamaievm.samplestoreapp.utils.PasswordUtils;
import com.griddynamics.mamaievm.samplestoreapp.utils.SaltedPassword;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)public interface UserMapper {
    
    default User toEntity(UserDto userDTO) {
        SaltedPassword saltedPassword = PasswordUtils.hashPassword(userDTO.getPassword());
        return User.builder()
                .email(userDTO.getEmail())
                .id(userDTO.getId())
                .hashedPassword(saltedPassword.getHashedPassword())
                .salt(saltedPassword.getSalt())
                .build();
    };

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)User partialUpdate(UserDto userDTO, @MappingTarget User user);

}