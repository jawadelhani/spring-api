package com.jawad.store.Controllers;

import com.jawad.store.dtos.CartDto;
import com.jawad.store.entities.Cart;
import com.jawad.store.mappers.CartMapper;
import com.jawad.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto=cartMapper.maptoDto(cart);

        //return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
        var uri=uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId());
        return ResponseEntity.created(uri.toUri()).body(cartDto);
    }

}
