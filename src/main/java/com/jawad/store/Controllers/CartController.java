package com.jawad.store.Controllers;

import com.jawad.store.dtos.AddItemToCartRequest;
import com.jawad.store.dtos.CartDto;
import com.jawad.store.dtos.CartItemDto;
import com.jawad.store.entities.Cart;
import com.jawad.store.entities.CartItem;
import com.jawad.store.mappers.CartMapper;
import com.jawad.store.repositories.CartRepository;
import com.jawad.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto=cartMapper.maptoDto(cart);

        //return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
        var uri=uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId());
        return ResponseEntity.created(uri.toUri()).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId,
                                                 @RequestBody AddItemToCartRequest request)
    {
        var cart=cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            return ResponseEntity.notFound().build();
        }
        var product=productRepository.findById(request.getProductId()).orElse(null);

        if(product==null){
            return ResponseEntity.badRequest().build();
        }

        //find product if already in cart ,if yes only add quantity ,if not add it to cart

        var cartItem=cart.getCartItems()
                .stream()
                .filter(i->i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            //cartItem can be null, not cartitem type
            cartItem=new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);

        var cartItemDto=cartMapper.toDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);




    }

}
