package com.example.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ecommerce.service.StatisticService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StatisticService statisticService;

	@GetMapping("/")
	public String home(Model model) {
		// Mã xử lý khi trang được load lần đầu tiên
		statisticService.prepareInitialData(model);
		return "admin/index";
	}

	@PostMapping("/")
	@ResponseBody
	public Map<String, List<?>> handleStatisticFilter(@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String filterType) {
		Map<String, List<?>> dataMap = new HashMap<>();
		if ("customer".equals(filterType)) {
			dataMap = statisticService.handleStatisticFilter(startDate, endDate, "customer");
		} else if ("product".equals(filterType)) {
			dataMap = statisticService.handleStatisticFilter(startDate, endDate, "product");
		} else if ("seller".equals(filterType)) {
			dataMap = statisticService.handleStatisticFilter(startDate, endDate, "seller");
		}
		return dataMap;
	}

	@GetMapping("/add-category")
	public String AddCategory() {
		return "admin/add-category";
	}

	@GetMapping("/add-product")
	public String AddProduct() {
		return "admin/add-product";
	}

	@GetMapping("/add-purchase-order")
	public String AddPurchaseOrder() {
		return "admin/add-purchase-order";
	}

	@GetMapping("/add-supplier")
	public String AddSupplier() {
		return "admin/add-supplier";
	}

	@GetMapping("/data-accounts")
	public String DataAccounts() {
		return "admin/data-accounts";
	}

	@GetMapping("/data-categories")
	public String DataCategories() {
		return "admin/data-categories";
	}

	@GetMapping("/data-orders")
	public String DataOrders() {
		return "admin/data-orders";
	}

	@GetMapping("/data-products")
	public String DataProducts() {
		return "admin/data-products";
	}

	@GetMapping("/data-suppliers")
	public String DataSuppliers() {
		return "admin/data-suppliers";
	}

	@GetMapping("/edit-account")
	public String EditAccount() {
		return "admin/edit-account";
	}

	@GetMapping("/edit-category")
	public String EditCategory() {
		return "admin/edit-category";
	}

	@GetMapping("/edit-product")
	public String EditProduct() {
		return "admin/edit-product";
	}

	@GetMapping("/edit-purchase-order")
	public String EditPurchaseOrder() {
		return "admin/edit-purchase-order";
	}

	@GetMapping("/edit-sale-order")
	public String EditSaleOrder() {
		return "admin/edit-sale-order";
	}

	@GetMapping("/edit-supplier")
	public String EditSupplier() {
		return "admin/edit-supplier";
	}

	@GetMapping("/error")
	public String Error() {
		return "admin/error";
	}

	@GetMapping("/forgor-password")
	public String ForgotPassword() {
		return "admin/forgor-password";
	}

	@GetMapping("/login")
	public String Login() {
		return "admin/login";
	}

	@GetMapping("/maintenance")
	public String Maintenance() {
		return "admin/maintenance";
	}

	@GetMapping("/signup")
	public String SignUp() {
		return "admin/signup";
	}
}