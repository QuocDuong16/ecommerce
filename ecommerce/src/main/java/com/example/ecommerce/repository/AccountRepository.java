package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	public Optional<Account> findByResetPasswordToken(String token);

	public Optional<Account> findByEmail(String email);

	public boolean existsByEmail(String email);
}
