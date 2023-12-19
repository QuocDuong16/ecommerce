package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.CartItemService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ShoppingCartService;

@Controller
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("/cart")
	public String viewCart(Model model) {
		Customer customer = customerService.findById(2);
		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
		List<CartItem> cartItems = shoppingCart.getCartItems();
		
		model.addAttribute("cartItems", cartItems);
		double cartTotal = cartItems.stream()
			    .mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity())
			    .sum();
		model.addAttribute("cartTotal", cartTotal);


		return "cart/cart";
	}

	@PostMapping("/add-to-cart")
	public String addToCart(@RequestParam int customerId, @RequestParam int productId, @RequestParam int quantity) {

		Customer customer = customerService.findById(customerId);
		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
		Product product = productService.findById(productId);

		// Thực hiện thêm vào giỏ hàng
		cartItemService.addToCart(shoppingCart, product, quantity);
		return "redirect:/shop";

	}

	@PostMapping("/remove-from-cart")
	public String removeFromCart(@RequestParam int customerId, @RequestParam int productId) {
		// Xử lý xóa sản phẩm khỏi giỏ hàng tại đây
		Customer customer = customerService.findById(customerId);
		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
		Product product = productService.findById(productId);

		cartItemService.removeFromCart(shoppingCart, product);

		return "redirect:/cart";
	}

	@PostMapping("/update-total-price")
	public String updateQuantity(@RequestParam int customerId, @RequestParam int productId,
			@RequestParam String direction) {
		Customer customer = customerService.findById(customerId);
		ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(customer.getAccountId());
		Product product = productService.findById(productId);
		cartItemService.updateQuantity(shoppingCart, product, direction);
		return "redirect:/cart";
	}

}
