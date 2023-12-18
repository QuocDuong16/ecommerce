package com.example.ecommerce.entity.Account;

import java.util.List;

import com.example.ecommerce.entity.ShoppingCart;
import com.example.ecommerce.entity.Order.SalesOrder;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/*
 * Khách hàng
 * Kế thừa từ Tài khoản với role được gán là customer
 * Người dùng có Giỏ hàng, và Hóa đơn mua hàng
 */
@Entity
@DiscriminatorValue("customer")
public class Customer extends Account {
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<SalesOrder> salesOrders;
	
	@OneToOne(mappedBy = "customer")
	private ShoppingCart shoppingCart;

	public Customer() {
		super();
	}

	public Customer(List<SalesOrder> salesOrders, ShoppingCart shoppingCart) {
		super();
		this.salesOrders = salesOrders;
		this.shoppingCart = shoppingCart;
	}

	public Customer(int accountId, String fullName, String password, String role, String address, String email,
			String phone, String resetPasswordToken, List<SalesOrder> salesOrders, ShoppingCart shoppingCart) {
		super(accountId, fullName, password, role, address, email, phone, resetPasswordToken);
		this.salesOrders = salesOrders;
		this.shoppingCart = shoppingCart;
	}

	public List<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Override
	public String getRole() {
		return "customer";
	}
}
