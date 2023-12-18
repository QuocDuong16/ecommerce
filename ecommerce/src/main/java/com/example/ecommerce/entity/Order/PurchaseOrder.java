package com.example.ecommerce.entity.Order;

import java.sql.Timestamp;
import java.util.List;

import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * Hóa đơn nhập hàng
 * Kế thừa từ Hóa đơn với order_type được gán là purchase
 * Có khóa ngoại là nhà cung cấp
 * Hóa đơn nhập hàng được tạo bởi admin được nhập bởi một nhà cung cấp với chi tiết hóa đơn chứa các Sản phẩm doanh nghiệp
 * Giống với Mall
 */
@Entity
@DiscriminatorValue("purchase")
public class PurchaseOrder extends Order {
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId", nullable = true)
    private Supplier supplier;

	public PurchaseOrder() {
		super();
	}

	public PurchaseOrder(Supplier supplier) {
		super();
		this.supplier = supplier;
	}

	public PurchaseOrder(int orderId, Timestamp orderDateCreate, List<OrderDetail> orderDetails, Supplier supplier) {
		super(orderId, orderDateCreate, orderDetails);
		this.supplier = supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}