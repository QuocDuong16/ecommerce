package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Key.CartItemKey;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {
}
