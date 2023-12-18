package com.example.ecommerce.entity.Product;

import java.sql.Blob;
import java.util.List;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * Sản phẩm (Class chung) được phân ra nhờ thuộc tính product_type được tạo bởi hibernate
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "tbl_product")
public abstract class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	@Lob
	private Blob productImage;
	private float productPrice;
	private String productDescription;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;
	private int productAmount;
	private String color;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

	public Product() {
		super();
	}

	public Product(int productId, String productName, Blob productImage, float productPrice,
			String productDescription, Category category, int productAmount, String color,
			List<OrderDetail> orderDetails, List<CartItem> cartItems) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productImage = productImage;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
		this.category = category;
		this.productAmount = productAmount;
		this.color = color;
		this.orderDetails = orderDetails;
		this.cartItems = cartItems;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Blob getProductImage() {
		return productImage;
	}

	public void setProductImage(Blob productImage) {
		this.productImage = productImage;
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
