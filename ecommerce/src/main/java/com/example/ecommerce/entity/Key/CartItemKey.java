package com.example.ecommerce.entity.Key;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/*
 * Khóa ảo được tạo bởi hibernate cho Chi tiết giỏ hàng
 */
@Embeddable
public class CartItemKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "shopping_cart_id")
    private int shoppingCartId;

    @Column(name = "product_id")
    private int productId;

	public CartItemKey() {
		super();
	}

	public CartItemKey(int shoppingCartId, int productId) {
		super();
		this.shoppingCartId = shoppingCartId;
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, shoppingCartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItemKey other = (CartItemKey) obj;
		return productId == other.productId && shoppingCartId == other.shoppingCartId;
	}

	public int getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(int shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
