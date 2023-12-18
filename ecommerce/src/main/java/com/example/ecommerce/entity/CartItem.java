package com.example.ecommerce.entity;

import com.example.ecommerce.entity.Key.CartItemKey;
import com.example.ecommerce.entity.Product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
/*
 * Chi tiết giỏ hàng
 * Có 2 khóa là Sản phẩm (product_id) và Giỏ hàng (shopping_cart_id)
 */
@Entity
@Table(name = "tbl_cart_item")
public class CartItem {
	@EmbeddedId
    private CartItemKey id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "productId", nullable = false)
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("shoppingCartId")
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shoppingCartId", nullable = false)
    private ShoppingCart shoppingCart;

    private int quantity;

	public CartItem() {
		super();
	}

	public CartItem(CartItemKey id, Product product, ShoppingCart shoppingCart, int quantity) {
		super();
		this.id = id;
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.quantity = quantity;
	}

	public CartItemKey getId() {
		return id;
	}

	public void setId(CartItemKey id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
