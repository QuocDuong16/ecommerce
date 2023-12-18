package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Account.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	public Admin findByEmail(String email);
}
