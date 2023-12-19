package com.example.ecommerce.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.PurchaseOrderService;
import com.example.ecommerce.service.SellerService;
import com.example.ecommerce.service.StatisticService;
import com.example.ecommerce.service.SupplierService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private StatisticService statisticService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PurchaseOrderService purchaseOrderService;

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

	@PostMapping("/add-to-category")
	public String AddCategory(@RequestParam("categoryName") String categoryName) {
		categoryService.addToCategory(categoryName);
		return "redirect:/admin/add-category";
	}

	@PostMapping("/add-to-supplier")
	public String AddSupplier(@RequestParam("supplierName") String supplierName,
			@RequestParam("supplierEmail") String supplierEmail,
			@RequestParam("supplierAddress") String supplierAddress) {
		supplierService.addToSupplier(supplierName, supplierEmail, supplierAddress);
		return "redirect:/admin/add-supplier";
	}

	@GetMapping("/add-product")
	public String AddProduct(Model model) {
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		List<Seller> sellers = sellerService.getAllSellers();
		List<Category> categories = categoryService.getAllCategories();

		model.addAttribute("suppliers", suppliers);
		model.addAttribute("sellers", sellers);
		model.addAttribute("categories", categories);
		
		return "admin/add-product";
	}
	@PostMapping("/add-to-product")
	public String addProduct(@RequestParam("productName") String productName,
							@RequestParam("productCategoryId") int productCategoryId,
	                         @RequestParam("productAmount") int productAmount,
	                         @RequestParam("productPrice") float productPrice,
	                         @RequestParam("productDescription") String productDescription,
	                         @RequestParam("productColor") String productColor,
	                         @RequestParam("image") MultipartFile image,
	                         @RequestParam("supplierId") int supplierId,
	                         @RequestParam("sellerId") int sellerId,
	                         @RequestParam("radio_product_type") boolean radio_product_type) {
	    // Your existing code to handle product creation
	    productService.addProduct(productName,productCategoryId, productAmount, productPrice, productDescription, productColor,image, supplierId, sellerId, radio_product_type);

	    // Redirect or return the appropriate view
	    return "redirect:/admin/add-product"; // Change this to your success view
	}



	@GetMapping("/add-purchase-order")
	public String AddPurchaseOrder(Model model) {
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		List<Product> products = productService.listAll();
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("products", products);
		return "admin/add-purchase-order";
	}
	@PostMapping("/add-to-purchase-order")
	public String AddPurchaseOrder(@RequestParam("supplierId") int supplierId,@RequestParam("due-date") String date,@RequestParam("orderDetailsRow") List<OrderDetail> orderDetailsRow) {
		purchaseOrderService.AddPurchaseOrder(supplierId, date, orderDetailsRow);
		return "redirect:/admin/add-purchase-order";
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