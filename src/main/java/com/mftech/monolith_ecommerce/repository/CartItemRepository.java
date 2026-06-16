package com.mftech.monolith_ecommerce.repository;

import com.mftech.monolith_ecommerce.model.CartItem;
import com.mftech.monolith_ecommerce.model.Product;
import com.mftech.monolith_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
    List<CartItem> findByUser(User user);
}
