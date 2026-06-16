package com.mftech.monolith_ecommerce.service;

import com.mftech.monolith_ecommerce.dto.OrderItemDTO;
import com.mftech.monolith_ecommerce.dto.OrderResponse;
import com.mftech.monolith_ecommerce.model.*;
import com.mftech.monolith_ecommerce.repository.CartItemRepository;
import com.mftech.monolith_ecommerce.repository.OrderItemRepository;
import com.mftech.monolith_ecommerce.repository.OrderRepository;
import com.mftech.monolith_ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(String userId) {
        Optional<User> optUser = userRepository.findById(Long.valueOf(userId));
        if (optUser.isEmpty()) throw new RuntimeException("User not found");
        User user = optUser.get();

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new RuntimeException("No items in cart");

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cartItems.stream().map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItems.stream().map(this::mapCartItemToOrderItem).toList();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartService.clearCart(user);

        return mapOrderToOrderResponse(order);
    }

    private OrderItem mapCartItemToOrderItem(CartItem cartItem){
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct().getPrice());
        return orderItem;
    }

    private OrderItemDTO mapOrderItemToOrderItemDTO(OrderItem orderItem){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        BeanUtils.copyProperties(orderItem, orderItemDTO);
        orderItemDTO.setSubTotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        return orderItemDTO;
    }

    private OrderResponse mapOrderToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        orderResponse.setOrderItems(order.getOrderItems().stream().map(this::mapOrderItemToOrderItemDTO).toList());
        return orderResponse;
    }
}
