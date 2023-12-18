package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Account.Admin;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.repository.AdminRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.SellerRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		Seller seller = sellerRepository.findByEmail(email);
		Admin admin = adminRepository.findByEmail(email);

		if (customer != null) {
			return new CustomUserDetail(customer);
		} else if (seller != null) {
			return new CustomUserDetail(seller);
		} else if (admin != null) {
			return new CustomUserDetail(admin);
		} else {
			throw new UsernameNotFoundException("User not found");
		}
	}
}