package com.mftech.monolith_ecommerce.service;

import com.mftech.monolith_ecommerce.dto.CartItemRequest;
import com.mftech.monolith_ecommerce.dto.CartItemResponse;
import com.mftech.monolith_ecommerce.dto.CartResponse;
import com.mftech.monolith_ecommerce.model.CartItem;
import com.mftech.monolith_ecommerce.model.Product;
import com.mftech.monolith_ecommerce.model.User;
import com.mftech.monolith_ecommerce.repository.CartItemRepository;
import com.mftech.monolith_ecommerce.repository.ProductRepository;
import com.mftech.monolith_ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartResponse addProductToCart(String userId, CartItemRequest cartItemRequest) {
        Optional<Product> optProduct = productRepository.findById(cartItemRequest.getProductId());
        if (optProduct.isEmpty() || optProduct.get().getIsActive() == false || optProduct.get().getStock() <= 0) throw new RuntimeException("Product not found");
        Product product = optProduct.get();
        Optional<User> optUser = userRepository.findById(Long.valueOf(userId));
        if (optUser.isEmpty()) throw new RuntimeException("User not found");
        User user = optUser.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(existingCartItem != null){
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(cartItemRequest.getQuantity());
            cartItemRepository.save(item);
        }
        return mapCartToCartResponse(cartItemRepository.findByUser(user));
    }

    public CartResponse getCartItems(String id) {
        Optional<User> optUser = userRepository.findById(Long.valueOf(id));
        if (optUser.isEmpty()) throw new RuntimeException("User not found");
        User user = optUser.get();
        return mapCartToCartResponse(cartItemRepository.findByUser(user));
    }

    public CartResponse removeProductFromCart(String userId, Long productId){
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isEmpty() || optProduct.get().getIsActive() == false || optProduct.get().getStock() <= 0) throw new RuntimeException("Product not found");
        Product product = optProduct.get();
        Optional<User> optUser = userRepository.findById(Long.valueOf(userId));
        if(optUser.isEmpty()) throw new RuntimeException("User not found");
        User user = optUser.get();
        CartItem optCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(optCartItem == null) throw new RuntimeException("Cart item not found");
        cartItemRepository.delete(optCartItem);
        return mapCartToCartResponse(cartItemRepository.findByUser(user));
    }

    public void clearCart(User user){
        cartItemRepository.deleteAll(cartItemRepository.findByUser(user));
    }

    private CartResponse mapCartToCartResponse(List<CartItem> cartItems){
        CartResponse cartItemResponse = new CartResponse();
        BigDecimal totalPrice = BigDecimal.ZERO;
        cartItemResponse.setCartItems(cartItems.stream().map(this::mapCartItemToCartItemResponse).toList());
        for(CartItem item : cartItems){
            totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        cartItemResponse.setTotalPrice(totalPrice);
        return cartItemResponse;
    }

    private CartItemResponse mapCartItemToCartItemResponse(CartItem cartItem){
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setName(cartItem.getProduct().getName());
        cartItemResponse.setPrice(cartItem.getProduct().getPrice());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setPack(cartItem.getProduct().getPack());
        cartItemResponse.setImage(cartItem.getProduct().getImage());
        cartItemResponse.setCategory(cartItem.getProduct().getCategory());
        cartItemResponse.setDescription(cartItem.getProduct().getDescription());
        return cartItemResponse;
    }
}
