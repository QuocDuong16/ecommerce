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
			@RequestParam("role") String role, @RequestParam("password") String password,
			@RequestParam("phone") String phone, @RequestParam("address") String address, HttpSession session) {

		boolean isCustomer = "customer".equals(role);
		boolean isSeller = "seller".equals(role);
		boolean isValidPhone = isValidPhoneNumber(phone);
		boolean isValidAddress = isValidAddress(address);
		if (isCustomer) {
			// Xử lý cho customer
			boolean emailExists = accountService.checkEmail(customer.getEmail());

			if (emailExists) {
				session.setAttribute("msg", "Email Id already exists");
			} else if (!isValidPhone) {
				session.setAttribute("msg", "Số điện thoại không hợp lệ, điện thoại 10 số và bắt đầu bằng 0");
			} else if (!isValidAddress) {
				session.setAttribute("msg", "Địa chỉ không hợp lệ, vui lòng địa chỉ không được full number");
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
			} else if (!isValidPhone) {
				session.setAttribute("msg", "Số điện thoại không hợp lệ, điện thoại 10 số và bắt đầu bằng 0");
			} else if (!isValidAddress) {
				session.setAttribute("msg", "Địa chỉ không hợp lệ, vui lòng địa chỉ không được full number");
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

	private boolean isValidPhoneNumber(String phone) {
		// Kiểm tra xem số điện thoại bắt đầu bằng 0 và có đúng 10 số hay không
		return phone != null && phone.matches("0\\d{9}");
	}

	private boolean isValidAddress(String address) {
		// Kiểm tra xem địa chỉ có ít nhất một chữ cái hay không
		return address != null && address.matches(".*[a-zA-Z]+.*");
	}
}