package com.example.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.service.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "restricted/login";
	}

	@GetMapping("/register")
	public String register() {
		return "restricted/register";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/user-page")
	public String userPage(Model model, Principal principal) {
//		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//		model.addAttribute("user", userDetails);
		return "user";
	}

	@PostMapping("/createUser")
	public String createuser(@ModelAttribute Customer customer, @ModelAttribute Seller seller,
			@RequestParam("role") String role, HttpSession session) {

		boolean isCustomer = "customer".equals(role);
		boolean isSeller = "seller".equals(role);

		if (isCustomer) {
			// Xử lý cho customer
			boolean emailExists = accountService.checkEmail(customer.getEmail());

			if (emailExists) {
				session.setAttribute("msg", "Email Id already exists");
			} else {
				// Gọi service để tạo customer
				accountService.createAccount(customer);
				session.setAttribute("msg", "Customer created successfully");
			}
		} else if (isSeller) {
			// Xử lý cho seller
			boolean emailExists = accountService.checkEmail(seller.getEmail());

			if (emailExists) {
				session.setAttribute("msg", "Email Id already exists");
			} else {
				// Gọi service để tạo seller
				accountService.createAccount(seller);
				session.setAttribute("msg", "Seller created successfully");
			}
		} else {
			session.setAttribute("msg", "Invalid role");
		}

		return "restricted/register";

	}
}