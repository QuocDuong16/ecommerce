package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;

@SpringBootTest
class ShoppingCartRepositoryTest {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Test
	public void save() {
		Customer customer = new Customer();
		customer.setAddress("HCM");
		customer.setEmail("congdang103@gmail.com");
		customer.setPassword("12345678");
		customer.setPhone("0123456789");
		customer.setFullName("Công Customer");
		
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCustomer(customer);
		
		shoppingCartRepository.save(shoppingCart);
	}

}
