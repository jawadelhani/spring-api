package com.jawad.store.mappers;

import com.jawad.store.dtos.CartDto;
import com.jawad.store.dtos.CartItemDto;
import com.jawad.store.entities.Cart;
import com.jawad.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto maptoDto(Cart cart);

    //mapp totalPrice field to a method (or another field if not same name)
    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
