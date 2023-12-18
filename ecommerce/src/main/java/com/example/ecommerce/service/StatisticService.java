package com.example.ecommerce.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.ecommerce.data.AnnualInfo;
import com.example.ecommerce.data.OrderInfo;

@Service
public class StatisticService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ProductService productService;

	private Model addRevenueData(Model model, int currentYear, int currentWeek) {
		float totalRevenueCurrentYear = salesOrderService.getTotalRevenueByYear(currentYear);
		float totalRevenueLastYear = salesOrderService.getTotalRevenueByYear(currentYear - 1);
		float revenueComparisonPercentage = Math
				.round(((totalRevenueCurrentYear - totalRevenueLastYear) / totalRevenueLastYear) * 10000.0) / 100.0f;

		List<Float> revenue12MonthList = salesOrderService.get12MonthRevenueByYear(currentYear);
		List<Float> revenueDayOfWeekList = salesOrderService.getRevenueByDayOfTheWeekByWeekAndByYear(currentYear,
				currentWeek);

		model.addAttribute("totalRevenueCurrentYear", totalRevenueCurrentYear);
		model.addAttribute("revenueComparisonPercentage", revenueComparisonPercentage);
		model.addAttribute("revenue12MonthList", revenue12MonthList);
		model.addAttribute("revenueDayOfWeekList", revenueDayOfWeekList);

		return model;
	}

	private Model addSalesData(Model model, int currentYear) {
		float totalSalesCurrentYear = salesOrderService.getTotalSalesByYear(currentYear);
		float totalSalesLastYear = salesOrderService.getTotalSalesByYear(currentYear - 1);
		float salesComparisonPercentage = Math
				.round(((totalSalesCurrentYear - totalSalesLastYear) / totalSalesLastYear) * 10000.0) / 100.0f;

		List<AnnualInfo> annualRevenueInfos = salesOrderService.getAnnualRevenue();
		List<Integer> annualRevenueyears = new ArrayList<>();
		List<Float> revenues = new ArrayList<>();

		for (AnnualInfo annualRevenueInfo : annualRevenueInfos) {
			annualRevenueyears.add(annualRevenueInfo.year());
			revenues.add(annualRevenueInfo.revenue());
		}

		List<AnnualInfo> annualSalesInfos = salesOrderService.getAnnualSales();

		model.addAttribute("totalSalesCurrentYear", totalSalesCurrentYear);
		model.addAttribute("salesComparisonPercentage", salesComparisonPercentage);
		model.addAttribute("annualRevenueyears", annualRevenueyears);
		model.addAttribute("revenues", revenues);
		model.addAttribute("annualSalesInfos", annualSalesInfos);

		return model;
	}

	private Model addTopCustomersData(Model model) {
		List<OrderInfo> orderCustomerInfos = customerService.findTopFiveCustomers();
		List<String> customerNames = new ArrayList<>();
		List<Integer> quantitieCustomers = new ArrayList<>();

		for (OrderInfo orderInfo : orderCustomerInfos) {
			customerNames.add(orderInfo.name());
			quantitieCustomers.add(orderInfo.quantity());
		}

		model.addAttribute("customerNames", customerNames);
		model.addAttribute("quantitieCustomers", quantitieCustomers);

		return model;
	}

	private Model addTopSellersData(Model model) {
		List<OrderInfo> orderSellerInfos = sellerService.findTopFiveSellers();
		List<String> sellerNames = new ArrayList<>();
		List<Integer> quantitieSellers = new ArrayList<>();

		for (OrderInfo orderInfo : orderSellerInfos) {
			sellerNames.add(orderInfo.name());
			quantitieSellers.add(orderInfo.quantity());
		}

		model.addAttribute("sellerNames", sellerNames);
		model.addAttribute("quantitieSellers", quantitieSellers);

		return model;
	}

	private Model addTopProductsData(Model model) {
		List<OrderInfo> orderProductInfos = productService.findTopTenProducts();
		List<String> productNames = new ArrayList<>();
		List<Integer> quantitieProducts = new ArrayList<>();

		for (OrderInfo orderInfo : orderProductInfos) {
			productNames.add(orderInfo.name());
			quantitieProducts.add(orderInfo.quantity());
		}

		model.addAttribute("productNames", productNames);
		model.addAttribute("quantitieProducts", quantitieProducts);

		return model;
	}

	public void prepareInitialData(Model model) {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

		addRevenueData(model, currentYear, currentWeek);
		addSalesData(model, currentYear);
		addTopCustomersData(model);
		addTopSellersData(model);
		addTopProductsData(model);
	}

	public Map<String, List<?>> handleStatisticFilter(String startDateStr, String endDateStr, String filterType) {
		Map<String, List<?>> dataMap = new HashMap<>();
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		switch (filterType) {
		case "customer":
			handleTopOrdersData(dataMap, customerService.findTopFiveCustomers(startDate, endDate), "customer");
			break;
		case "product":
			handleTopOrdersData(dataMap, productService.findTopTenProducts(startDate, endDate), "product");
			break;
		case "seller":
			handleTopOrdersData(dataMap, sellerService.findTopFiveSellers(startDate, endDate), "seller");
			break;
		default:
			break;
		}

		return dataMap;
	}

	private void handleTopOrdersData(Map<String, List<?>> dataMap, List<OrderInfo> orderInfos, String type) {
		List<String> names = new ArrayList<>();
		List<Integer> quantities = new ArrayList<>();

		for (OrderInfo orderInfo : orderInfos) {
			names.add(orderInfo.name());
			quantities.add(orderInfo.quantity());
		}

		dataMap.put(type + "Names", names);
		dataMap.put("quantitie" + Character.toUpperCase(type.charAt(0)) + type.substring(1) + "s", quantities);
	}
}
