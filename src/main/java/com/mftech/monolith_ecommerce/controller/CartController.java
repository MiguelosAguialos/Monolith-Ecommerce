package com.mftech.monolith_ecommerce.controller;

import com.mftech.monolith_ecommerce.dto.CartItemRequest;
import com.mftech.monolith_ecommerce.dto.CartItemResponse;
import com.mftech.monolith_ecommerce.dto.CartResponse;
import com.mftech.monolith_ecommerce.dto.ProductResponse;
import com.mftech.monolith_ecommerce.model.CartItem;
import com.mftech.monolith_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping()
    public ResponseEntity<CartResponse> getCartItems(@RequestHeader("X-User-ID") String userId) {
        return new ResponseEntity<>(cartService.getCartItems(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartResponse> addProductToCart(@RequestHeader("X-User-ID") String userId,
                                                         @RequestBody CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(cartService.addProductToCart(userId, cartItemRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestHeader("X-User-ID") String userId,
                                                             @PathVariable Long productId) {
        return new ResponseEntity<>(cartService.removeProductFromCart(userId, productId),HttpStatus.OK);
    }
}
