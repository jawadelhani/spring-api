package com.jawad.store.mappers;

import com.jawad.store.dtos.UserDto;
import com.jawad.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

}
