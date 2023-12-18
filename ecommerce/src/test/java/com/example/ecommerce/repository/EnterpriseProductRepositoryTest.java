package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Product.EnterpriseProduct;

@SpringBootTest
class EnterpriseProductRepositoryTest {

	@Autowired
	private EnterpriseProductRepository enterpriseProductRepository;
	
	@Test
	public void save() {
		Category category = new Category();
		category.setCategoryName("Laptop");
		
		Supplier supplier = new Supplier();
		supplier.setAddress("HN");
		supplier.setEmail("lapworld@gmail.com");
		supplier.setSupplierName("Laptop World");
		
		EnterpriseProduct enterpriseProduct = new EnterpriseProduct();
		enterpriseProduct.setProductName("Asus vivobook");
		enterpriseProduct.setColor("White");
		enterpriseProduct.setProductDescription("Samsung");
		enterpriseProduct.setProductPrice(23000000);
		enterpriseProduct.setProductAmount(20);
		enterpriseProduct.setCategory(category);
		enterpriseProduct.setSupplier(supplier);
		
		enterpriseProductRepository.save(enterpriseProduct);
	}

}
