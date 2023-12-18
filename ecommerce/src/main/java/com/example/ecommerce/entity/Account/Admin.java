package com.example.ecommerce.entity.Account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/*
 * Admin
 * Kế thừa từ Tài khoản với role được gán là admin
 */
@Entity
@DiscriminatorValue("admin")
public class Admin extends Account {

	public Admin() {
		super();
	}

	public Admin(int accountId, String fullName, String password, String role, String address, String email,
			String phone, String resetPasswordToken) {
		super(accountId, fullName, password, role, address, email, phone, resetPasswordToken);
	}

	@Override
	public String getRole() {
		return "admin";
	}
}
