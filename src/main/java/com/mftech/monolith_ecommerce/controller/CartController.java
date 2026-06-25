package com.mftech.monolith_ecommerce.controller;

import com.mftech.monolith_ecommerce.dto.CartItemRequest;
import com.mftech.monolith_ecommerce.dto.CartItemResponse;
import com.mftech.monolith_ecommerce.dto.CartResponse;
import com.mftech.monolith_ecommerce.dto.ProductResponse;
import com.mftech.monolith_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Operacoes do carrinho de compras do cliente")
public class CartController {

    private final CartService cartService;

    @GetMapping()
    @Operation(summary = "Consultar carrinho", description = "Retorna os itens do carrinho e o total acumulado do usuario identificado pelo header X-User-ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrinho retornado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "404", description = "Carrinho nao encontrado")
    })
    public ResponseEntity<CartResponse> getCartItems(@RequestHeader("X-User-ID") String userId) {
        return new ResponseEntity<>(cartService.getCartItems(userId),HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adicionar item ao carrinho", description = "Adiciona um produto ao carrinho do usuario ou incrementa a quantidade existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item adicionado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisicao invalida"),
            @ApiResponse(responseCode = "404", description = "Produto ou carrinho nao encontrado")
    })
    public ResponseEntity<CartResponse> addProductToCart(@RequestHeader("X-User-ID") String userId,
                                                         @RequestBody CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(cartService.addProductToCart(userId, cartItemRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Remover item do carrinho", description = "Remove completamente um produto do carrinho do usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item removido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto nao encontrado no carrinho")
    })
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestHeader("X-User-ID") String userId,
                                                             @PathVariable Long productId) {
        return new ResponseEntity<>(cartService.removeProductFromCart(userId, productId),HttpStatus.OK);
    }
}
