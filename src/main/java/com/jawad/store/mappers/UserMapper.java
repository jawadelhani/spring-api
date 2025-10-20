package com.jawad.store.mappers;

import com.jawad.store.dtos.RegisterUserRequest;
import com.jawad.store.dtos.UpdateUserRequest;
import com.jawad.store.dtos.UserDto;
import com.jawad.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void updateUser(UpdateUserRequest request,@MappingTarget User user);

}
