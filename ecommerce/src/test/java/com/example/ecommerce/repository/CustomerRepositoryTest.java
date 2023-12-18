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
import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Order.SalesOrder;

@SpringBootTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;
	
//	@Test
//	public void save() {
//		Customer customer = new Customer();
//		customer.setAddress("HCM");
//		customer.setEmail("nguyenquocduong380@gmail.com");
//		customer.setPassword("16052002");
//		customer.setPhone("0788807068");
//		customer.setFullName("Dương Customer");
//		customerRepository.save(customer);
//	}
	
//	@Test
//	public void update() {
//		Customer customer = customerRepository.findById(1).get();
//		customer.setFullName("Dương Customer");
//		customerRepository.save(customer);
//	}
//	
//	@Test
//	public void delete() {
//		Customer customer = customerRepository.findById(1).get();
//		customerRepository.delete(customer);
//	}
	
	@Test
	public void getCustomer() {
		try {
			List<OrderInfo> purchaseInfos = new ArrayList<OrderInfo>();
			
			List<Customer> customers = customerRepository.findBySalesOrdersIsNotNull();
			
			for (Customer customer : customers) {
				int quantity = 0;
				for (SalesOrder salesOrder : customer.getSalesOrders()) {
					for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
						quantity += orderDetail.getQuantity();
					}
				}
				purchaseInfos.add(new OrderInfo(customer.getFullName(), quantity));
			}
			
			Collections.sort(purchaseInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));
			
			System.out.println(purchaseInfos.size() < 4 ? purchaseInfos : purchaseInfos.subList(0, 4));
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}
