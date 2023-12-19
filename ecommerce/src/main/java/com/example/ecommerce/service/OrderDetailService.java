package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.repository.OrderDetailRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private CartItemService cartItemService;
	
	public void save(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	@Transactional
	public List<OrderDetail> getOrderDetailsForPayment(Customer customer) {
		SalesOrder order = salesOrderService.saveAndGet(customer);

		List<OrderDetail> orderDetails = new ArrayList<>();

		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());

		for (CartItem cartItem : shoppingCart.getCartItems()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setId(new OrderDetailKey());
			orderDetail.setOrder(order);
			orderDetail.setProduct(cartItem.getProduct());
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetails.add(orderDetail);
		}
		orderDetailRepository.saveAll(orderDetails);
		// xóa cartitem
		cartItemService.deleteAllInBatch(shoppingCart.getCartItems());

		return orderDetails;
	}
}