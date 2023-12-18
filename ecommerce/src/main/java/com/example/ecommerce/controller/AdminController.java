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

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Admin;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.service.AccountService;
import com.example.ecommerce.service.AdminService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EnterpriseProductService;
import com.example.ecommerce.service.IndividualProductService;
import com.example.ecommerce.service.PurchaseOrderService;
import com.example.ecommerce.service.SalesOrderService;
import com.example.ecommerce.service.SellerService;
import com.example.ecommerce.service.StatisticService;
import com.example.ecommerce.service.SupplierService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private IndividualProductService individualProductService;
	
	@Autowired
	private EnterpriseProductService enterpriseProductService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private SupplierService supplierService;

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
	public String DataAccounts(Model model) {
		List<Admin> admins = adminService.getAdmins();
		List<Customer> customers = customerService.getCustomers();
		List<Seller> sellers = sellerService.getSellers();
		
		
		model.addAttribute("admins", admins);
		model.addAttribute("customers", customers);
		model.addAttribute("sellers", sellers);
		return "admin/data-accounts";
	}

	@GetMapping("/data-categories")
	public String DataCategories(Model model) {
		List<Category> categories = categoryService.getCategories();
		
		model.addAttribute("categories", categories);
		return "admin/data-categories";
	}

	@GetMapping("/data-orders")
	public String DataOrders(Model model) {
		List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrders();
		List<SalesOrder> salesOrders = salesOrderService.getSalesOrders();
		
		model.addAttribute("purchaseOrders", purchaseOrders);
		model.addAttribute("salesOrders", salesOrders);
		return "admin/data-orders";
	}

	@GetMapping("/data-products")
	public String DataProducts(Model model) {
		List<IndividualProduct> individualProducts = individualProductService.getIndividualProducts();
		List<EnterpriseProduct> enterpriseProducts = enterpriseProductService.getEnterpriseProducts();
		
		model.addAttribute("individualProducts", individualProducts);
		model.addAttribute("enterpriseProducts", enterpriseProducts);
		return "admin/data-products";
	}

	@GetMapping("/data-suppliers")
	public String DataSuppliers(Model model) {
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		
		model.addAttribute("suppliers", suppliers);
		return "admin/data-suppliers";
	}

	@GetMapping("/edit-account")
	public String EditAccount(Model model) {
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