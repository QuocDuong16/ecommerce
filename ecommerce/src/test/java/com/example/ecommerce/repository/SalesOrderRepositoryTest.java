package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.data.AnnualInfo;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Order.SalesOrder;


@SpringBootTest
class SalesOrderRepositoryTest {

	@Autowired
	private SalesOrderRepository salesOrderRepository;
	
	@Test
	public void test() {
		List<AnnualInfo> annualInfos = new ArrayList<AnnualInfo>();
		
		List<Integer> years = salesOrderRepository.findDistinctYears();
		
		for (Integer year : years) {
			float revenue = 0;
			for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYear(year)) {
				for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
					revenue += orderDetail.getQuantity() * orderDetail.getProduct().getProductPrice();
				}
			}
			annualInfos.add(new AnnualInfo(year, revenue, null));
		}
		
		System.out.println(annualInfos);
	}
	
	@Test
	public void test2() {
		List<Integer> monthList = List.of(1,2,3,4,5,6,7,8,9,10,11,12);
		
		List<AnnualInfo> annualInfos = new ArrayList<AnnualInfo>();
		
		List<Integer> years = salesOrderRepository.findDistinctYears();
		
		for (Integer year : years) {
			List<Integer> salesList = new ArrayList<Integer>();
			for (Integer month : monthList) {
				int sales = 0;
				for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYearAndMonth(year, month)) {
					for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
						sales += orderDetail.getQuantity();
					}
				}
				salesList.add(sales);
			}
			annualInfos.add(new AnnualInfo(year, null, salesList));
		}
		
		System.out.println(annualInfos);
	}
	
}
