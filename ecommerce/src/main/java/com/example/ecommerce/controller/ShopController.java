package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShopController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/shop-single/{productId}")
    public String showProductDetails(@PathVariable int productId,HttpServletRequest request,  Model model) {
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	Customer customer = customerService.findById(userId);
    	Product product = productService.findById(productId);
        
        model.addAttribute("product", product);
        model.addAttribute("customer", customer);
        
        return "shop-single";
    }
}