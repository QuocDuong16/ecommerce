package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Key.CartItemKey;
import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.entity.Product.Product;

import jakarta.transaction.Transactional;

@SpringBootTest
class CartItemRepositoryTest {

	@Autowired
	private CartItemRepository cartItemRepository;
	
//	@Test
//	public void save() {
//		Customer customer = new Customer();
//		customer.setFullName("Duy Customer 2");
//		customer.setAddress("HCM");
//		customer.setEmail("nguyenthanhduy3@gmail.com");
//		customer.setPassword("12345678");
//		customer.setPhone("0123456789");
//		
//		Seller seller = new Seller();
//		seller.setAddress("HCM");
//		seller.setEmail("nguyenthanhduy2@gmail.com");
//		seller.setPassword("12345678");
//		seller.setPhone("0123456789");
//		seller.setFullName("Duy Seller 2");
//		
//		Category category = new Category();
//		category.setCategoryId(1);
//		category.setCategoryName("Điện thoại");
//		
//		IndividualProduct individualProduct = new IndividualProduct();
//		individualProduct.setColor("Red");
//		individualProduct.setProductAmount(12);
//		individualProduct.setProductName("Iphone 14");
//		individualProduct.setProductPrice(30000000);
//		individualProduct.setCategory(category);
//		individualProduct.setSeller(seller);
//		
//		ShoppingCart shoppingCart = new ShoppingCart();
//		shoppingCart.setCustomer(customer);
//		
//		CartItem cartItem = new CartItem();
//		cartItem.setId(new CartItemKey());
//		cartItem.setProduct(individualProduct);
//		cartItem.setShoppingCart(shoppingCart);
//		cartItem.setQuantity(2);
//		
//		cartItemRepository.save(cartItem);
//	}
	@Transactional
	@Test
	public void delete() {
		cartItemRepository.deleteById(new CartItemKey(11, 7));
	}

}
