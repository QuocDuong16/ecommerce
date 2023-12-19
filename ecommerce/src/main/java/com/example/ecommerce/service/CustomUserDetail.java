package com.example.ecommerce.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ecommerce.entity.Account.Admin;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;

public class CustomUserDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private Seller seller;
	private Admin admin;

	// Constructor for Customer
	public CustomUserDetail(Customer customer) {
		this.customer = customer;
	}

	// Constructor for Seller
	public CustomUserDetail(Seller seller) {
		this.seller = seller;
	}

	// Constructor for Admin
	public CustomUserDetail(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (customer != null) {
			return List.of(() -> customer.getRole());
		} else if (seller != null) {
			return List.of(() -> seller.getRole());
		} else if (admin != null) {
			return List.of(() -> admin.getRole());
		} else {
			return Collections.emptyList();
		}
	}

	public String getFullname() {
		if (customer != null) {
			return customer.getFullName();
		} else if (seller != null) {
			return seller.getFullName();
		} else if (admin != null) {
			return admin.getFullName();
		} else {
			return null;
		}
	}

	@Override
	public String getPassword() {
		if (customer != null) {
			return customer.getPassword();
		} else if (seller != null) {
			return seller.getPassword();
		} else if (admin != null) {
			return admin.getPassword();
		} else {
			return null;
		}
	}

	@Override
	public String getUsername() {
		if (customer != null) {
			return customer.getEmail();
		} else if (seller != null) {
			return seller.getEmail();
		} else if (admin != null) {
			return admin.getEmail();
		} else {
			return null;
		}
	}

	public int getUserId() {
		if (customer != null) {
			// Assuming Customer class has a getUserId() method
			return customer.getAccountId();
		} else if (seller != null) {
			// Assuming Seller class has a getUserId() method
			return seller.getAccountId();
		} else if (admin != null) {
			// Assuming Admin class has a getUserId() method
			return admin.getAccountId();
		} else {
			return (Integer) null;
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}