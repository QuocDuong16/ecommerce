package com.example.ecommerce.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.data.AnnualInfo;
import com.example.ecommerce.data.OrderDetailRequest;
import com.example.ecommerce.data.PurchaseOrderRequest;
import com.example.ecommerce.data.SalesOrderRequest;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.repository.SalesOrderRepository;

@Service
public class SalesOrderService {
	@Autowired
	private SalesOrderRepository salesOrderRepository;
	
	@Autowired
	private CustomerService customerService;

	public Float getTotalRevenueByYear(int year) {
		float revenue = 0;
		for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYear(year)) {
			for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
				revenue += orderDetail.getQuantity() * orderDetail.getProduct().getProductPrice();
			}
		}
		return revenue;
	}

	public Float getTotalSalesByYear(int year) {
		float sales = 0;
		for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYear(year)) {
			for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
				sales += orderDetail.getQuantity();
			}
		}
		return sales;
	}

	public List<AnnualInfo> getAnnualRevenue() {
		List<AnnualInfo> annualInfos = new ArrayList<AnnualInfo>();

		List<Integer> years = salesOrderRepository.findDistinctYears();

		for (Integer year : years) {
			float revenue = getTotalRevenueByYear(year);
			annualInfos.add(new AnnualInfo(year, revenue, null));
		}
		return annualInfos;

	}

	public List<AnnualInfo> getAnnualSales() {
		List<Integer> monthList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

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
		return annualInfos;
	}

	public List<Float> get12MonthRevenueByYear(int year) {
		List<Integer> monthList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

		List<Float> revenueList = new ArrayList<Float>();

		for (Integer month : monthList) {
			float revenue = 0;
			for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYearAndMonth(year, month)) {
				for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
					revenue += orderDetail.getQuantity() * orderDetail.getProduct().getProductPrice();
				}
			}
			revenueList.add(revenue);
		}
		return revenueList;
	}

	public List<Float> getRevenueByDayOfTheWeekByWeekAndByYear(int year, int week) {
		List<Integer> dayList = List.of(1, 2, 3, 4, 5, 6, 7);
		List<Float> revenueList = new ArrayList<Float>();
		for (Integer day : dayList) {
			float revenue = 0;
			for (SalesOrder salesOrder : salesOrderRepository.findByOrderDateCreateYearWeekAndDayOfWeek(year, week,
					day)) {
				for (OrderDetail orderDetail : salesOrder.getOrderDetails()) {
					revenue += orderDetail.getQuantity() * orderDetail.getProduct().getProductPrice();
				}
			}
			revenueList.add(revenue);
		}
		return revenueList;
	}

	public void save(SalesOrder salesOrder) {
		salesOrderRepository.save(salesOrder);
	}

	public SalesOrder saveAndGet(Customer customer) {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setCustomer(customer);
		return salesOrderRepository.save(salesOrder);
	}
	
	public List<SalesOrder> getSalesOrders() {
		return salesOrderRepository.findAll();
	}
	
	public SalesOrder getSalesOrderById(int id) {
		return salesOrderRepository.findById(id).orElse(null);
	}
	
	public void update(SalesOrder salesOrder) {
		salesOrderRepository.save(salesOrder);
	}
	
	public void update(SalesOrderRequest request) {
		SalesOrder salesOrder = getSalesOrderById(request.orderId);
		Customer customer = customerService.getCustomerById(request.customerId);
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        try {
            Date date = sdfInput.parse(request.orderDateCreate);

            long timestamp = date.getTime();
            salesOrder.setOrderDateCreate(new Timestamp(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
		salesOrder.setCustomer(customer);
		for (OrderDetail orderDetail: salesOrder.getOrderDetails()) {
			for (OrderDetailRequest o: request.orderDetails) {
				if (orderDetail.getId().equals(new OrderDetailKey(Integer.valueOf(o.orderId), Integer.valueOf(o.productId)))) {
					orderDetail.setQuantity(Integer.valueOf(o.quantity));
				}
			}
		}
		
		salesOrderRepository.save(salesOrder);
	}
}
