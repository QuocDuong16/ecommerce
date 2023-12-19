package com.example.ecommerce.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.data.OrderInfo;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void addProduct(String productName, int productCategoryId, int productAmount, float productPrice,
			String productDescription, String productColor, int supplierId, int sellerId, boolean radio_product_type) {

		Product product;
		if (radio_product_type) {
			EnterpriseProduct enterpriseProduct = new EnterpriseProduct();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(supplierId);
			enterpriseProduct.setSupplier(supplier);
			product = enterpriseProduct;
		} else {
			IndividualProduct individualProduct = new IndividualProduct();
			Seller seller = new Seller();
			seller.setAccountId(sellerId);
			individualProduct.setSeller(seller);
			product = individualProduct;
		}

// Common properties for both types
		product.setProductName(productName);
		product.setProductAmount(productAmount);
		product.setProductPrice(productPrice);
		product.setProductDescription(productDescription);
		product.setColor(productColor);

		Category category = new Category();
		category.setCategoryId(productCategoryId);
		product.setCategory(category);

		productRepository.save(product);
	}
	public void addProduct(String productName, int supplierId, int sellerId, boolean radio_product_type) {

		Product product;
		if (radio_product_type) {
			EnterpriseProduct enterpriseProduct = new EnterpriseProduct();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(supplierId);
			enterpriseProduct.setSupplier(supplier);
			product = enterpriseProduct;
		} else {
			IndividualProduct individualProduct = new IndividualProduct();
			Seller seller = new Seller();
			seller.setAccountId(sellerId);
			individualProduct.setSeller(seller);
			product = individualProduct;
		}

// Common properties for both types
		product.setProductName(productName);

		productRepository.save(product);
	}

	public List<OrderInfo> findTopTenProducts() {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

		List<Product> products = productRepository.findByOrderDetailsIsNotNull();

		for (Product product : products) {
			int quantity = 0;
			for (OrderDetail orderDetail : product.getOrderDetails()) {
				quantity += orderDetail.getQuantity();
			}
			orderInfos.add(new OrderInfo(product.getProductName(), quantity));
		}
		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));
		return orderInfos.size() < 10 ? orderInfos : orderInfos.subList(0, 10);
	}

	public List<OrderInfo> findTopTenProducts(LocalDate startDate, LocalDate endDate) {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

		List<Product> products = productRepository.findByOrderDetailsIsNotNull();

		for (Product product : products) {
			int quantity = 0;
			for (OrderDetail orderDetail : product.getOrderDetails()) {
				Timestamp orderDateCreate = orderDetail.getOrder().getOrderDateCreate();
				LocalDate localDate = orderDateCreate.toLocalDateTime().toLocalDate();

				if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
					quantity += orderDetail.getQuantity();
				}
			}
			if (quantity == 0)
				continue;
			orderInfos.add(new OrderInfo(product.getProductName(), quantity));
		}
		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));
		return orderInfos.size() < 10 ? orderInfos : orderInfos.subList(0, 10);
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Product findById(int productId) {
		return productRepository.findById(productId).orElse(null);
	}

	public List<Product> listAll() {
		return productRepository.findAll();
	}

	public List<Product> searchByName(String name) {
		// Sử dụng phương thức findBy<FieldName>ContainingIgnoreCase để thực hiện tìm
		// kiếm
		return productRepository.findByProductNameContainingIgnoreCase(name);
	}

	public List<Product> searchByColor(String color) {
		return productRepository.findByColorIgnoreCase(color);
	}

	public List<Product> searchByPriceRange(float minPrice, float maxPrice) {
		return productRepository.findByProductPriceBetween(minPrice, maxPrice);
	}
}
