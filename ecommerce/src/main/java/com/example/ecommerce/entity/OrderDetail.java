package com.example.ecommerce.entity;

import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Order.Order;
import com.example.ecommerce.entity.Product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;


/*
 * Chi tiết hóa đơn order
 * Có 2 khóa là Sản phẩm (product_id) và Hóa đơn (order_id)
 */
@Entity
@Table(name = "tbl_order_detail")
public class OrderDetail {
	@EmbeddedId
	private OrderDetailKey id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "orderId", nullable = false)
	private Order order;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "productId", nullable = false)
	private Product product;
	
	private int quantity;

	public OrderDetail() {
		super();
	}

	public OrderDetail(OrderDetailKey id, Order order, Product product, int quantity) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public OrderDetailKey getId() {
		return id;
	}

	public void setId(OrderDetailKey id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

