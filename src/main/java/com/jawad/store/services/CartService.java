package com.jawad.store.services;

import com.jawad.store.dtos.CartDto;
import com.jawad.store.entities.Cart;
import com.jawad.store.mappers.CartMapper;
import com.jawad.store.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.maptoDto(cart);
    }

}
