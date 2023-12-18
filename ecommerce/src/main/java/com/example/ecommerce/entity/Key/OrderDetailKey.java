package com.example.ecommerce.entity.Key;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/*
 * Khóa ảo được tạo bởi hibernate cho Chi tiết hóa đơn
 */
@Embeddable
public class OrderDetailKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "order_id")
    private int orderId;

    @Column(name = "product_id")
    private int productId;

	public OrderDetailKey() {
		super();
	}

	public OrderDetailKey(int orderId, int productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailKey other = (OrderDetailKey) obj;
		return orderId == other.orderId && productId == other.productId;
	}
}
