package com.example.ecommerce.controller;

import java.util.HashSet;
import java.util.List; // Correct import statement
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.entity.Product.Product;
import java.net.URLDecoder;
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	private void addUniqueColorsAndProductsToModel(List<Product> allProducts, Model model) {
		Set<String> uniqueColors = new HashSet<>();
		List<String> uniqueProductColors = allProducts.stream().map(Product::getColor)
				.filter(color -> uniqueColors.add(color)).collect(Collectors.toList());

		model.addAttribute("uniqueProductColors", uniqueProductColors);
		model.addAttribute("products", allProducts);
	}

	@GetMapping("/shop")
	public String shop(Model model) {
		List<Product> products = productService.listAll();
		addUniqueColorsAndProductsToModel(products, model);
		return "shop";
	}

	@GetMapping("/search")
	public String doSearchProduct(@RequestParam(name = "productName", required = false) String productName,
			Model model) {
		List<Product> products = productService.listAll();

		if (productName != null && !productName.isEmpty()) {
			addUniqueColorsAndProductsToModel(productService.searchByName(productName), model);
		} else {
			addUniqueColorsAndProductsToModel(products, model);
		}

		return "shop";
	}

	@GetMapping("/searchcolor")
	public String doSearchProductByColor(@RequestParam(name = "productColor", required = false) String productColor,
	                                      Model model) {
	    List<Product> products = productService.listAll();
	    List<Product> searchResults;

	    if (productColor != null && !productColor.isEmpty()) {
	        try {
	            // Giải mã màu nếu được truyền dưới dạng "%23" thay vì "#"
	            productColor = URLDecoder.decode(productColor, "UTF-8");
	        } catch (Exception e) {
	            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi giải mã
	            model.addAttribute("errorMessage", "Invalid color format");
	            addUniqueColorsAndProductsToModel(products, model);
	            return "shop";
	        }

	        // Kiểm tra nếu màu có đúng định dạng "#...."
	        if (productColor.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
	            searchResults = productService.searchByColor(productColor);
	        } else {
	            // Xử lý khi màu không đúng định dạng
	            model.addAttribute("errorMessage", "Invalid color format");
	            addUniqueColorsAndProductsToModel(products, model);
	            return "shop";
	        }
	    } else {
	        addUniqueColorsAndProductsToModel(products, model);
	        return "shop";
	    }

	    addUniqueColorsAndProductsToModel(searchResults, model);
	    return "shop";
	}


	@GetMapping("/searchprice")
	public String doSearchProductByPriceRange(@RequestParam(name = "minPrice", required = false) Float minPrice,
			@RequestParam(name = "maxPrice", required = false) Float maxPrice, Model model) {

		List<Product> searchResults;

		if (minPrice != null && maxPrice != null) {
			searchResults = productService.searchByPriceRange(minPrice, maxPrice);
		} else if (minPrice != null) {
			searchResults = productService.searchByPriceRange(minPrice, Float.MAX_VALUE);
		} else if (maxPrice != null) {
			searchResults = productService.searchByPriceRange(Float.MIN_VALUE, maxPrice);
		} else {
			searchResults = productService.listAll();
		}

		addUniqueColorsAndProductsToModel(searchResults, model);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);

		return "shop";
	}
}
