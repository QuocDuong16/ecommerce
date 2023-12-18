package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Order.Order;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.entity.Product.Product;

@SpringBootTest
class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Test
	public void saveWithSalesOrder() {
		Customer customer = new Customer();
		customer.setAccountId(6);
		customer.setFullName("Duy Customer 1");
		customer.setAddress("HCM");
		customer.setEmail("nguyenthanhduy220507@gmail.com");
		customer.setPassword("12345678");
		customer.setPhone("0123456789");
		
		Seller seller = new Seller();
		seller.setAccountId(10);
		seller.setAddress("HCM");
		seller.setEmail("nguyenthanhduy1@gmail.com");
		seller.setPassword("12345678");
		seller.setPhone("0123456789");
		seller.setFullName("Duy Seller 1");
		
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("Điện thoại");
		
		IndividualProduct individualProduct = new IndividualProduct();
		individualProduct.setColor("Red");
		individualProduct.setProductAmount(12);
		individualProduct.setProductDescription("Apple");
		individualProduct.setProductName("Iphone 13A");
		individualProduct.setProductPrice(200);
		individualProduct.setCategory(category);
		individualProduct.setSeller(seller);
		
		SalesOrder order = new SalesOrder();
		order.setOrderId(4);
		order.setCustomer(customer);
		order.setOrderDateCreate(new Timestamp(System.currentTimeMillis()));
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(new OrderDetailKey());
		orderDetail.setQuantity(100);
		orderDetail.setProduct(individualProduct);
		orderDetail.setOrder(order);
		try {
			orderDetailRepository.save(orderDetail);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
//	@Test
//	public void saveWithPurchaseOrder() {
//		Category category = new Category();
//		category.setCategoryId(2);
//		category.setCategoryName("Laptop");
//		
//		Supplier supplier = new Supplier();
//		supplier.setAddress("HCM");
//		supplier.setEmail("lapworld@gmail.com");
//		supplier.setSupplierName("Laptop World");
//		
//		EnterpriseProduct enterpriseProduct = new EnterpriseProduct();
//		enterpriseProduct.setProductName("Asus vivobook 2023");
//		enterpriseProduct.setColor("White");
//		enterpriseProduct.setProductDescription("Samsung");
//		enterpriseProduct.setProductPrice(25000000);
//		enterpriseProduct.setProductAmount(20);
//		enterpriseProduct.setCategory(category);
//		enterpriseProduct.setSupplier(supplier);
//		
//		PurchaseOrder order = new PurchaseOrder();
//		order.setSupplier(supplier);
//		
//		OrderDetail orderDetail = new OrderDetail();
//		orderDetail.setId(new OrderDetailKey());
//		orderDetail.setQuantity(30);
//		orderDetail.setProduct(enterpriseProduct);
//		orderDetail.setOrder(order);
//		orderDetailRepository.save(orderDetail);
//	}
	
//	@Test
//	public void delete() {
//		OrderDetail orderDetail = orderDetailRepository.findById(new OrderDetailKey(4, 3)).get();
//		orderDetailRepository.delete(orderDetail);
//	}

}
