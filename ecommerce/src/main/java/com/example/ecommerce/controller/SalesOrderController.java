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
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.service.CartItemService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.OrderDetailService;
import com.example.ecommerce.service.ShoppingCartService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SalesOrderController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/check-out")
	public String checkout(Model model, HttpServletRequest request) {
		// @RequestParam int customerId
		Integer userId = (Integer) request.getSession().getAttribute("userId");
//		Customer customer = customerService.findById(userId);
//		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
//		List<CartItem> cartItems = shoppingCart.getCartItems();
//		double cartTotal = cartItems.stream()
//				.mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum();
//		model.addAttribute("cartTotal", cartTotal);
//
//		model.addAttribute("cartItems", cartItems);
////    	SalesOrder order = new SalesOrder(customer);
////        sellerOrderService.save(order);
//
//		model.addAttribute("customer", customer);
		
		if (userId != null) {
			Customer customer = customerService.findById(userId);
			ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
			List<CartItem> cartItems = shoppingCart.getCartItems();
			double cartTotal = cartItems.stream()
					.mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum();
			model.addAttribute("cartTotal", cartTotal);

			model.addAttribute("cartItems", cartItems);
			model.addAttribute("customer", customer);
	        return "cart/check-out";
	    } else {
	        // Xử lý khi userId không tồn tại trong session
	        return "redirect:/error";
	    }
	}

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