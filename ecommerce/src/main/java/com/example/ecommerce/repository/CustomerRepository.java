package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Account.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public List<Customer> findBySalesOrdersIsNotNull();

	public Customer findByResetPasswordToken(String token);

	public boolean existsByEmail(String email);

	public Customer findByEmail(String email);
}
