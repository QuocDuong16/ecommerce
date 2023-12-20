package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@PostMapping("/confirm-send-email")
	public String confirmSendEmail(@ModelAttribute Customer customer) {
		Customer updatedCustomer = customerService.update(customer);
		try {
			emailService.sendVerificationEmail(updatedCustomer, "Xác nhân email",
					orderDetailService.getOrderDetailsForPayment(updatedCustomer));

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return "redirect:/thankyou";
	}

	@GetMapping("/thankyou")
	public String thankyou(Model model) {
		return "thankyou";
	}

}