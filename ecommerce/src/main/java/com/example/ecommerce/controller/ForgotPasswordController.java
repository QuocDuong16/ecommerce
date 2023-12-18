package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.service.AccountService;

@Controller
public class ForgotPasswordController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AccountService accountService;

	@GetMapping("/forgot")
	public String forgotPassword() {
		return "restricted/forgot_password";
	}

	@PostMapping("/forgot_password")
	public String sendResetLink(@RequestParam("email") String email, Model model) {
		// TODO: Generate and store a unique token for password reset
		String resetToken = generateResetToken();
		accountService.storeResetToken(email, resetToken);

		// Create the email message
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Password Reset");
		message.setText("Click on the link to reset your password: http://localhost:8080/reset?token=" + resetToken);

		// Send the email
		javaMailSender.send(message);

		// TODO: Add logic to inform the user that the reset link has been sent

		return "restricted/forgot_password";
	}

	@GetMapping("/reset")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
		// Kiểm tra xem token có hợp lệ không
		boolean isTokenValid = accountService.isResetTokenValid(token);

		if (isTokenValid) {
			// Token hợp lệ, hiển thị trang đặt lại mật khẩu
			model.addAttribute("token", token);
			return "restricted/reset_password_form";
		} else {
			// Token không hợp lệ, xử lý theo logic của bạn (ví dụ: chuyển hướng đến trang
			// thông báo lỗi)
			return "redirect:/error";
		}
	}

	@PostMapping("/reset_password")
	public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String newPassword,
			Model model) {
		try {
			// Reset the password
			accountService.resetPassword(token, newPassword);

			// Add a success message
			model.addAttribute("message", "Password reset successful. You can now log in with your new password.");
		} catch (Exception e) {
			// Handle other unexpected exceptions
			model.addAttribute("error", "An error occurred while resetting the password.");
			return "redirect:/error";
		}

		// Redirect to the login page or any other appropriate page
		return "redirect:/login";
	}

	private String generateResetToken() {
		// TODO: Implement logic to generate a unique token
		return "your-unique-token";
	}
}
