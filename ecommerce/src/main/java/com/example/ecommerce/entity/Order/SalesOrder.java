package com.example.ecommerce.entity.Order;

import java.sql.Timestamp;
import java.util.List;

import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * Hóa đơn bán
 * Kế thừa từ Hóa đơn với order_type được gán là sales
 * Có khóa ngoại là khách hàng
 * Hóa đơn bán hàng được tạo thành bởi khách hàng với chi tiết hóa đơn chứa các Sản phẩm (cá nhân hoặc doanh nghiệp)
 */
@Entity
@DiscriminatorValue("sales")
public class SalesOrder extends Order {
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "account_id", referencedColumnName = "accountId", nullable = true)
    private Customer customer;

	public SalesOrder() {
		super();
	}

	public SalesOrder(Customer customer) {
		super();
		this.customer = customer;
	}

	public SalesOrder(int orderId, Timestamp orderDateCreate, List<OrderDetail> orderDetails, Customer customer) {
		super(orderId, orderDateCreate, orderDetails);
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

