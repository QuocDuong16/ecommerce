package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.data.OrderInfo;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.entity.Product.IndividualProduct;

@SpringBootTest
class SellerRepositoryTest {
	
	@Autowired
	private SellerRepository sellerRepository;

//	@Test
//	public void save() {
//		Seller seller = new Seller();
//		seller.setAddress("HCM");
//		seller.setEmail("nguyenquocduong1605@gmail.com");
//		seller.setPassword("16052002");
//		seller.setPhone("0788807068");
//		seller.setFullName("Dương Seller");
//		sellerRepository.save(seller);
//	}
//	
//	@Test
//	public void update() {
//		Seller seller = sellerRepository.findById(2).get();
//		seller.setFullName("Dương Seller");
//		sellerRepository.save(seller);
//	}
//	
//	@Test
//	public void delete() {
//		Seller seller = sellerRepository.findById(2).get();
//		sellerRepository.delete(seller);
//	}
	
	@Test
	public void test() {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

		List<Seller> sellers = sellerRepository.findByIndividualProductsIsNotNull();

		Boolean flag = false;
		
		for (Seller seller : sellers) {
			int quantity = 0;
			for (IndividualProduct individualProduct : seller.getIndividualProducts()) {
				if (individualProduct.getOrderDetails().isEmpty()) {
					flag = true;
					continue;
				}
				flag = false;
				for (OrderDetail orderDetail : individualProduct.getOrderDetails()) {
					quantity += orderDetail.getQuantity();
				}
			}
			if (flag) continue;
			orderInfos.add(new OrderInfo(seller.getFullName(), quantity));
		}

		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));

		System.out.println(orderInfos.size() < 5 ? orderInfos : orderInfos.subList(0, 5));
	}
//	
//	@Test
//	public void test2() {
//		for (Seller seller : sellerRepository.findByIndividualProductsIsNotNull()) {
//			for (IndividualProduct individualProduct : seller.getIndividualProducts()) {
//				System.out.println(individualProduct.getOrderDetails().isEmpty());
//			}
//		}
//	}

}
