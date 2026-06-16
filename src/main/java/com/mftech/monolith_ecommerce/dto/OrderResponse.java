package com.mftech.monolith_ecommerce.dto;

import com.mftech.monolith_ecommerce.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
