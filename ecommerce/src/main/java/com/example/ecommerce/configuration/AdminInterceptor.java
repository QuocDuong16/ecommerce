package com.example.ecommerce.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Kiểm tra nếu URL bắt đầu bằng "/admin"
        if (request.getRequestURI().startsWith("/admin")) {
            // Kiểm tra cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userRole".equals(cookie.getName()) && "admin".equals(cookie.getValue())) {
                        // Người dùng có quyền admin, cho phép truy cập
                        return true;
                    }
                }
            }

            // Người dùng không có quyền admin, chuyển hướng hoặc xử lý khác
            response.sendRedirect("/admin/error");
            return false;
        }

        // Cho phép các URL khác không phải "/admin" truy cập mà không kiểm tra
        return true;
    }
}