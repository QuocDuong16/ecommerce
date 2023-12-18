package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Account.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	public List<Seller> findByIndividualProductsIsNotNull();

	public Seller findByResetPasswordToken(String token);

	public boolean existsByEmail(String email);

	public Seller findByEmail(String email);
}
