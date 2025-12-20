package com.jawad.store.mappers;

import com.jawad.store.dtos.CartDto;
import com.jawad.store.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto maptoDto(Cart cart);
}
