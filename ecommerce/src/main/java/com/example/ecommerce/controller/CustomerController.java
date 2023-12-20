package com.example.ecommerce.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

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
			searchResults = productService.searchByColor(productColor);
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

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/shop-single/{productId}")
	public String showProductDetails(@PathVariable int productId, HttpServletRequest request, Model model) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		Customer customer = customerService.findById(userId);
		Product product = productService.findById(productId);

		model.addAttribute("product", product);
		model.addAttribute("customer", customer);

		return "shop-single";
	}

	@GetMapping("/cart")
	public String viewCart(HttpServletRequest request, Model model) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		Customer customer = customerService.findById(userId);
		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
		List<CartItem> cartItems = shoppingCart.getCartItems();

		model.addAttribute("cartItems", cartItems);
		double cartTotal = cartItems.stream()
				.mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum();
		model.addAttribute("cartTotal", cartTotal);

		return "cart/cart";
	}

	@GetMapping("/check-out")
	public String checkout(Model model, HttpServletRequest request) {
		// @RequestParam int customerId
		Integer userId = (Integer) request.getSession().getAttribute("userId");
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
}