package com.example.ecommerce.entity.Product;

import java.sql.Blob;
import java.util.List;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * Sản phẩm doanh nghiệp
 * Kế thừa từ Sản phẩm với product_type được gán là enterprise
 * Được tạo bởi admin, nhập vào từ hóa đơn nhập hàng của nhà cung cấp
 */
@Entity
 @DiscriminatorValue("enterprise")
public class EnterpriseProduct extends Product {
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
	private Supplier supplier;

	public EnterpriseProduct() {
		super();
	}

	public EnterpriseProduct(Supplier supplier) {
		super();
		this.supplier = supplier;
	}

	public EnterpriseProduct(int productId, String productName, Blob productImage, float productPrice,
			String productDescription, Category category, int productAmount, String color,
			List<OrderDetail> orderDetails, List<CartItem> cartItems, Supplier supplier) {
		super(productId, productName, productImage, productPrice, productDescription, category, productAmount, color,
				orderDetails, cartItems);
		this.supplier = supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String getType() {
		return "enterprise";
	}
}
