package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Key.CartItemKey;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.repository.CartItemRepository;

@Service
public class CartItemService {
	@Autowired
	private CartItemRepository cartItemRepository;

	public void addToCart(ShoppingCart shoppingCart, Product product, int quantity) {
		Optional<CartItem> existingCartItem = cartItemRepository
				.findById(new CartItemKey(shoppingCart.getShoppingCartId(), product.getProductId()));
		if (existingCartItem.isPresent()) {
			CartItem cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItemRepository.save(cartItem);
		} else {
			CartItem newCartItem = new CartItem();
			newCartItem.setId(new CartItemKey());
			newCartItem.setProduct(product);
			newCartItem.setShoppingCart(shoppingCart);
			newCartItem.setQuantity(quantity);
			cartItemRepository.save(newCartItem);
		}
	}

	public void removeFromCart(ShoppingCart shoppingCart, Product product) {
		Optional<CartItem> existingCartItem = cartItemRepository
				.findById(new CartItemKey(shoppingCart.getShoppingCartId(), product.getProductId()));
		CartItem cartItem = existingCartItem.get();
		System.out.println(cartItem.getId().getShoppingCartId());
		System.out.println(cartItem.getId().getProductId());
		try {
			cartItemRepository.deleteAllInBatch(List.of(cartItem));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// update
	public void updateQuantity(ShoppingCart shoppingCart, Product product, String direction) {
		Optional<CartItem> existingCartItem = cartItemRepository
				.findById(new CartItemKey(shoppingCart.getShoppingCartId(), product.getProductId()));

		existingCartItem.ifPresent(cartItem -> {
			int updatedQuantity = cartItem.getQuantity();
			if (direction.equals("+")) {
				updatedQuantity += 1;
			} else if (direction.equals("-")) {
				updatedQuantity -= 1;
			}

			if (updatedQuantity > 0) {
				cartItem.setQuantity(updatedQuantity);
				cartItemRepository.save(cartItem);
			} else {
				// Nếu số lượng bằng 0 hoặc âm, xóa sản phẩm khỏi giỏ hàng
				cartItemRepository.delete(cartItem);
			}
		});
	}

	public void save(CartItem cartItem) {
		cartItemRepository.save(cartItem);
	}

	public void deleteAllInBatch(List<CartItem> cartItems) {
		cartItemRepository.deleteAllInBatch(cartItems);
	}

	public List<CartItem> getCartItems() {
		return cartItemRepository.findAll();
	}

	// Các phương thức khác liên quan đến CartItem
}
