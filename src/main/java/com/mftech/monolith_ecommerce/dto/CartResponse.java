package com.mftech.monolith_ecommerce.dto;

import com.mftech.monolith_ecommerce.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private List<CartItemResponse> cartItems;
    private BigDecimal totalPrice;
}
