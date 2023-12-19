package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Account.Admin;
import com.example.ecommerce.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	public List<Admin> getAdmins() {
		return adminRepository.findAll();
	}
	
	public void update(Admin admin) {
		adminRepository.save(admin);
	}
}
