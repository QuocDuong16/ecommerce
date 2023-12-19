package com.example.ecommerce.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		var authorities = authentication.getAuthorities();
		var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();

		if (roles.isPresent()) {
			String role = roles.get();
			// Set Role Cookie
			setCookie(response, "userRole", role);

			// Set UserID Cookie (Assuming you have a method to retrieve user ID)
			int userId = getUserId(authentication);
			if (userId != -1) {
				setCookie(response, "userId", String.valueOf(userId));
				// Lưu userId vào session để sử dụng trong các controller khác
		        request.getSession().setAttribute("userId", userId);
			}

			switch (role) {
			case "admin":
				response.sendRedirect("/admin/");
				break;
			case "customer":
				response.sendRedirect("/customer/");
				break;
			case "seller":
				response.sendRedirect("/seller/");
				break;
			default:
				response.sendRedirect("/error");
			}
		} else {
			response.sendRedirect("/error");
		}
	}

	private void setCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private int getUserId(Authentication authentication) {
		// Implement your logic to retrieve the user ID from the authentication object
		// For example, if your UserDetail class has a method like getUserId(), you can
		// call it.
		if (authentication.getPrincipal() instanceof CustomUserDetail) {
			CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
			return userDetails.getUserId();
		}
		return -1; // Return a default value indicating that the user ID is not found
	}
}
