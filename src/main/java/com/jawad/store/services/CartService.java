package com.jawad.store.services;

import com.jawad.store.dtos.CartDto;
import com.jawad.store.dtos.CartItemDto;
import com.jawad.store.entities.Cart;
import com.jawad.store.exceptions.CartNotFoundException;
import com.jawad.store.exceptions.ProductNotFoundException;
import com.jawad.store.mappers.CartMapper;
import com.jawad.store.repositories.CartRepository;
import com.jawad.store.repositories.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.maptoDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart=cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw new CartNotFoundException();
        }
        var product=productRepository.findById(productId).orElse(null);

        if(product==null){
            throw new ProductNotFoundException();
        }

        var cartItem=cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.maptoDto(cart);
    }

    public CartItemDto updateItem(UUID cartId,Long productId,Integer quantity){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var cartItem = cart.getItem(productId);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void removeItem(UUID cartId,Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }

}
