package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.ProductService;

@Controller
public class ShopController {
    @Autowired
    private ProductService productService;
    
    @GetMapping("/shop-single/{productId}")
    public String showProductDetails(@PathVariable int productId,  Model model) {
        
    	Product product = productService.findById(productId);
        
        model.addAttribute("product", product);
        
        return "shop-single";
    }
}