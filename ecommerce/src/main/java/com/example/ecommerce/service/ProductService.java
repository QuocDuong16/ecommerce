package com.example.ecommerce.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.data.ImageUtils;
import com.example.ecommerce.data.OrderInfo;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.repository.EnterpriseProductRepository;
import com.example.ecommerce.repository.IndividualProductRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private EnterpriseProductRepository enterpriseProductRepository;

	public Product getProductById(int id) {
		return productRepository.findById(id).orElse(null);
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

	public void addProduct(String productName, int productCategoryId, int productAmount, float productPrice,
			String productDescription, String productColor, MultipartFile image, int supplierId, int sellerId,
			boolean radio_product_type) {
		Category category = categoryService.getCategoryById(productCategoryId);

		if (!radio_product_type) {
			EnterpriseProduct enterpriseProduct = new EnterpriseProduct();
			Supplier supplier = supplierService.getSupplierById(supplierId);
			enterpriseProduct.setSupplier(supplier);
			enterpriseProduct.setProductName(productName);
			enterpriseProduct.setProductAmount(productAmount);
			enterpriseProduct.setProductPrice(productPrice);
			enterpriseProduct.setProductDescription(productDescription);
			enterpriseProduct.setColor(productColor);
			try {
				byte[] resizedImageBytes = ImageUtils.resizeImage(image, 250, 250);
				Blob imageBlob = ImageUtils.convertToBlob(resizedImageBytes);
				enterpriseProduct.setProductImage(imageBlob);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			enterpriseProduct.setCategory(category);
			enterpriseProductRepository.save(enterpriseProduct);
		} else {
			IndividualProduct individualProduct = new IndividualProduct();
			Seller seller = sellerService.getSellerById(sellerId);
			individualProduct.setSeller(seller);
			individualProduct.setProductName(productName);
			individualProduct.setProductAmount(productAmount);
			individualProduct.setProductPrice(productPrice);
			individualProduct.setProductDescription(productDescription);
			individualProduct.setColor(productColor);
			try {
				byte[] resizedImageBytes = ImageUtils.resizeImage(image, 250, 250);
				Blob imageBlob = ImageUtils.convertToBlob(resizedImageBytes);
				individualProduct.setProductImage(imageBlob);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			individualProduct.setCategory(category);
			individualProductRepository.save(individualProduct);
		}
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

	public void deleteProductById(Integer productId) {
		// Kiểm tra xem tài khoản có tồn tại hay không
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			// Nếu tồn tại, thực hiện xóa
			Product product = optionalProduct.get();
			productRepository.delete(product);
		} else {
			// Nếu không tồn tại, có thể thực hiện xử lý thông báo hoặc ném một exception
			// tùy thuộc vào yêu cầu của bạn
			// Ví dụ: throw new NotFoundException("Account not found with ID: " +
			// accountId);
		}
	}
}
