package com.example.ecommerce.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CustomerService customerService;

	public ShoppingCart getUserShoppingCart(int accountId) {
		// Lấy thông tin khách hàng (người dùng) dựa trên accountId
		Customer customer = customerService.findById(accountId);

		if (customer != null) {
			// Kiểm tra xem khách hàng đã có giỏ hàng hay chưa
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);

			if (shoppingCart == null) {
				shoppingCart = new ShoppingCart();
				shoppingCart.setCustomer(customer);
				shoppingCart.setCartItems(new ArrayList<>());
				shoppingCart = shoppingCartRepository.save(shoppingCart);
			}

			return shoppingCart;
		}

		return null;
	}
}
