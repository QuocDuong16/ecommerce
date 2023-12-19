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
			setRoleCookie(response, role);

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

	private void setRoleCookie(HttpServletResponse response, String role) {
		Cookie cookie = new Cookie("userRole", role);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
