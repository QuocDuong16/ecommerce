package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.Account.Admin;

@SpringBootTest
class AdminRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void save() {
		Admin admin = new Admin();
		admin.setAddress("HCM");
		admin.setEmail("nguyenquocduonggpt@gmail.com");
		admin.setPassword("16052002");
		admin.setPhone("0788807068");
		admin.setFullName("Dương Admin");
		adminRepository.save(admin);
	}
//	
//	@Test
//	public void update() {
//		Admin admin = adminRepository.findById(3).get();
//		admin.setFullName("Dương Admin");
//		adminRepository.save(admin);
//	}
//	
//	@Test
//	public void delete() {
//		Admin admin = adminRepository.findById(3).get();
//		adminRepository.delete(admin);
//	}

}
