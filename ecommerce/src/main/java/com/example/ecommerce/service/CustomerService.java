package com.example.ecommerce.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.data.OrderInfo;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public List<OrderInfo> findTopFiveCustomers() {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

		List<Customer> customers = customerRepository.findBySalesOrdersIsNotNull();

		for (Customer customer : customers) {
			int quantity = 0;
			for (SalesOrder salesOrder : customer.getSalesOrders()) {
				for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
					quantity += orderDetail.getQuantity();
				}
			}
			orderInfos.add(new OrderInfo(customer.getFullName(), quantity));
		}

		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));

		return orderInfos.size() < 5 ? orderInfos : orderInfos.subList(0, 5);
	}

	public List<OrderInfo> findTopFiveCustomers(LocalDate startDate, LocalDate endDate) {
		List<OrderInfo> orderInfos = new ArrayList<>();

		List<Customer> customers = customerRepository.findBySalesOrdersIsNotNull();

		for (Customer customer : customers) {
			int quantity = 0;
			for (SalesOrder salesOrder : customer.getSalesOrders()) {
				Timestamp orderDateCreate = salesOrder.getOrderDateCreate();
				LocalDate localDate = orderDateCreate.toLocalDateTime().toLocalDate();

				if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
					for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
						quantity += orderDetail.getQuantity();
					}
				}
			}
			if (quantity == 0)
				continue;
			orderInfos.add(new OrderInfo(customer.getFullName(), quantity));
		}

		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));

		return orderInfos.size() < 5 ? orderInfos : orderInfos.subList(0, 5);
	}

	public Customer findById(int accountId) {
		return customerRepository.findById(accountId).orElse(null);
	}
	public void update(Customer customer) {
		customerRepository.save(customer);
	}
}
