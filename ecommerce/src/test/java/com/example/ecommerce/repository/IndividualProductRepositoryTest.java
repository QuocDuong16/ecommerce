package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Product.IndividualProduct;

@SpringBootTest
class IndividualProductRepositoryTest {

	@Autowired
	private IndividualProductRepository individualProductRepository;
	
	@Test
	public void save() {
		Category category = new Category();
		category.setCategoryName("Điện thoại");
		
		Seller seller = new Seller();
		seller.setAddress("HCM");
		seller.setEmail("congdang102@gmail.com");
		seller.setPassword("12345678");
		seller.setPhone("0123456789");
		seller.setFullName("Công Seller");
		
		IndividualProduct individualProduct = new IndividualProduct();
		individualProduct.setColor("Red");
		individualProduct.setProductAmount(12);
		individualProduct.setProductName("Iphone 11");
		individualProduct.setProductPrice(10000000);
		individualProduct.setCategory(category);
		individualProduct.setSeller(seller);
		
		individualProductRepository.save(individualProduct);
	}

}
