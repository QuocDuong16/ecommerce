package com.example.ecommerce.entity;

import java.util.List;

import com.example.ecommerce.entity.Account.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * Giỏ hàng
 * Có khóa ngoại đến Account - Customer
 * Chỉ có khách hàng mới có thể mua sản phẩm
 */
@Entity
@Table(name = "tbl_shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingCartId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "account_id", referencedColumnName = "accountId", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

	public ShoppingCart() {
		super();
	}

	public ShoppingCart(int shoppingCartId, Customer customer, List<CartItem> cartItems) {
		super();
		this.shoppingCartId = shoppingCartId;
		this.customer = customer;
		this.cartItems = cartItems;
	}

	public int getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(int shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}

