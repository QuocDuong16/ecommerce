package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
	public ShoppingCart findByCustomer(Customer customer);
}
