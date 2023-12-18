package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.service.CartItemService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.OrderDetailService;

import jakarta.mail.MessagingException;

@Controller
public class SalesOrderController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/check-out")
	public String checkout(Model model) {
		// @RequestParam int customerId
		List<CartItem> cartItems = cartItemService.getCartItems();
		double cartTotal = cartItems.stream()
				.mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum();
		model.addAttribute("cartTotal", cartTotal);

		model.addAttribute("cartItems", cartItems);

		Customer customer = customerService.findById(5);
		customer.getFullName();
		customer.getAddress();
		customer.getPhone();
//    	SalesOrder order = new SalesOrder(customer);
//        sellerOrderService.save(order);

		model.addAttribute("Customer", customer);

		return "cart/check-out";
	}

	@PostMapping("/confirm-send-email")
	public String confirmSendEmail(@RequestParam("c_fullname") String fullName,
			@RequestParam("c_address") String address, @RequestParam("c_phone") String phone) {
		Customer customer = customerService.findById(5);
		customer.setFullName(fullName);
		customer.setAddress(address);
		customer.setPhone(phone);

		try {
			emailService.sendVerificationEmail(customer, "Xác nhân email",
					orderDetailService.getOrderDetailsForPayment(customer));
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		// ystem.out.println("3");
		// return "verification-email";
		return "redirect:/thankyou";
	}

	@GetMapping("/thankyou")
	public String thankyou(Model model) {
		return "thankyou";
	}

}