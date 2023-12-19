package com.example.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Order.Order;
import com.example.ecommerce.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public void deleteOrderById(Integer productId) {
		// Kiểm tra xem tài khoản có tồn tại hay không
		Optional<Order> optionalOrder = orderRepository.findById(productId);
		if (optionalOrder.isPresent()) {
			// Nếu tồn tại, thực hiện xóa
			Order order = optionalOrder.get();
			orderRepository.delete(order);
		} else {

		}
	}
}
