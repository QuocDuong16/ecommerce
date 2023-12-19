package com.example.ecommerce.entity.Product;

import java.sql.Blob;
import java.util.List;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Seller;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * Sản phẩm cá nhân
 * Kế thừa từ Sản phẩm với product_type được gán là individual
 * Được tạo bởi người bán
 */
@Entity
@DiscriminatorValue("individual")
public class IndividualProduct extends Product {
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "account_id", referencedColumnName = "accountId")
	private Seller seller;

	public IndividualProduct() {
		super();
	}

	public IndividualProduct(Seller seller) {
		super();
		this.seller = seller;
	}

	public IndividualProduct(int productId, String productName, Blob productImage, float productPrice,
			String productDescription, Category category, int productAmount, String color,
			List<OrderDetail> orderDetails, List<CartItem> cartItems, Seller seller) {
		super(productId, productName, productImage, productPrice, productDescription, category, productAmount, color,
				orderDetails, cartItems);
		this.seller = seller;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@Override
	public String getType() {
		return "individual";
	}
}
