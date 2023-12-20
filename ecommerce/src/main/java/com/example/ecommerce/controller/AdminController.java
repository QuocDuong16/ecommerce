package com.example.ecommerce.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.data.ImageUtils;
import com.example.ecommerce.data.PurchaseOrderRequest;
import com.example.ecommerce.data.SalesOrderRequest;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.entity.Account.Admin;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Order.SalesOrder;
import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.service.AccountService;
import com.example.ecommerce.service.AdminService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EnterpriseProductService;
import com.example.ecommerce.service.IndividualProductService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.PurchaseOrderService;
import com.example.ecommerce.service.SalesOrderService;
import com.example.ecommerce.service.SellerService;
import com.example.ecommerce.service.StatisticService;
import com.example.ecommerce.service.SupplierService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private AccountService accountService;

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

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

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
		return "redirect:/admin/data-categories";
	}

	@GetMapping("/add-product")
	public String AddProduct(Model model) {
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		List<Seller> sellers = sellerService.getSellers();
		List<Category> categories = categoryService.getCategories();

		model.addAttribute("suppliers", suppliers);
		model.addAttribute("sellers", sellers);
		model.addAttribute("categories", categories);

		return "admin/add-product";
	}

	@PostMapping("/add-to-product")
	public String addProduct(@RequestParam("productName") String productName,
			@RequestParam("productCategoryId") int productCategoryId, @RequestParam("productAmount") int productAmount,
			@RequestParam("productPrice") float productPrice,
			@RequestParam("productDescription") String productDescription,
			@RequestParam("productColor") String productColor, @RequestParam("image") MultipartFile image,
			@RequestParam("supplierId") int supplierId, @RequestParam("sellerId") int sellerId,
			@RequestParam("radio_product_type") boolean radio_product_type) {
		// Your existing code to handle product creation
		productService.addProduct(productName, productCategoryId, productAmount, productPrice, productDescription,
				productColor, image, supplierId, sellerId, radio_product_type);

		// Redirect or return the appropriate view
		return "redirect:/admin/data-products"; // Change this to your success view
	}

	@GetMapping("/add-purchase-order")
	public String AddPurchaseOrder(Model model) {
		List<EnterpriseProduct> products = enterpriseProductService.getEnterpriseProducts();
		List<Supplier> suppliers = supplierService.getAllSuppliers();

		model.addAttribute("suppliers", suppliers);
		model.addAttribute("products", products);
		return "admin/add-purchase-order";
	}

	@PostMapping("/add-purchase-order")
	public String executeAddPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
		purchaseOrderService.add(request);

		return "redirect:/admin/data-orders";
	}

	@GetMapping("/add-supplier")
	public String AddSupplier() {
		return "admin/add-supplier";
	}

	@PostMapping("/add-to-supplier")
	public String AddSupplier(@RequestParam("supplierName") String supplierName,
			@RequestParam("supplierEmail") String supplierEmail,
			@RequestParam("supplierAddress") String supplierAddress) {
		supplierService.addToSupplier(supplierName, supplierEmail, supplierAddress);
		return "redirect:/admin/data-suppliers";
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

	@GetMapping("/edit-account/{accountId}")
	public String EditAccount(@PathVariable int accountId, Model model) {
		Account account = accountService.getAccountById(accountId);
		if (account == null) {
			return "redirect:/admin/error";
		}
		model.addAttribute("account", account);
		model.addAttribute("roleLink", account.getRole());

		return "admin/edit-account";
	}

	@PostMapping("/edit-admin")
	public String executeEditAdmin(@ModelAttribute Admin account) {
		adminService.update(account);

		return "redirect:/admin/data-accounts";
	}

	@PostMapping("/edit-seller")
	public String executeEditSeller(@ModelAttribute Seller account) {
		sellerService.update(account);

		return "redirect:/admin/data-accounts";
	}

	@PostMapping("/edit-customer")
	public String executeEditCustomer(@ModelAttribute Customer account) {
		customerService.update(account);

		return "redirect:/admin/data-accounts";
	}

	@GetMapping("/edit-category/{categoryId}")
	public String EditCategory(@PathVariable int categoryId, Model model) {
		Category category = categoryService.getCategoryById(categoryId);
		if (category == null) {
			return "redirect:/admin/error";
		}
		model.addAttribute("category", category);
		return "admin/edit-category";
	}

	@PostMapping("/edit-category")
	public String executeEditCategory(@ModelAttribute Category category) {
		categoryService.update(category);

		return "redirect:/admin/data-categories";
	}

	@GetMapping("/edit-product/{productId}")
	public String EditProduct(@PathVariable int productId, Model model) {
		Product product = productService.getProductById(productId);

		if (product == null) {
			return "redirect:/admin/error";
		}

		String base64Image = "";
		if (product.getProductImage() != null) {
			try {
				Blob blob = Hibernate.unproxy(product.getProductImage(), Blob.class);
				byte[] bytes = blob.getBytes(1, (int) blob.length());
				base64Image = Base64.getEncoder().encodeToString(bytes);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		List<Category> categories = categoryService.getCategories();
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		List<Seller> sellers = sellerService.getSellers();

		model.addAttribute("product", product);
		model.addAttribute("type", product.getType());
		model.addAttribute("categories", categories);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("sellers", sellers);
		model.addAttribute("base64Image", base64Image);

		return "admin/edit-product";
	}

	@PostMapping("/edit-individual")
	public String executeEditIndividualProduct(@ModelAttribute IndividualProduct product,
			@RequestParam("image") MultipartFile image) {
		try {
			byte[] resizedImageBytes = ImageUtils.resizeImage(image, 250, 250);
			Blob imageBlob = ImageUtils.convertToBlob(resizedImageBytes);
			product.setProductImage(imageBlob);
			individualProductService.update(product);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/data-products";
	}

	@PostMapping("/edit-enterprise")
	public String executeEditEnterpriseProduct(@ModelAttribute EnterpriseProduct product,
			@RequestParam("image") MultipartFile image) {
		try {
			byte[] resizedImageBytes = ImageUtils.resizeImage(image, 250, 250);
			Blob imageBlob = ImageUtils.convertToBlob(resizedImageBytes);
			product.setProductImage(imageBlob);
			enterpriseProductService.update(product);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/data-products";
	}

	@GetMapping("/edit-purchase-order/{orderId}")
	public String EditPurchaseOrder(@PathVariable int orderId, Model model) {
		PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(orderId);
		List<EnterpriseProduct> products = enterpriseProductService.getEnterpriseProducts();
		List<Supplier> suppliers = supplierService.getAllSuppliers();

		if (purchaseOrder == null || purchaseOrder.getOrderDetails().isEmpty()) {
			return "redirect:/admin/error";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		String formattedDate = sdf.format(new Date(purchaseOrder.getOrderDateCreate().getTime()));
		model.addAttribute("formattedDate", formattedDate);
		model.addAttribute("orderDetails", purchaseOrder.getOrderDetails());
		model.addAttribute("totalPrice", purchaseOrder.calculateTotalPrice());
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("products", products);
		model.addAttribute("order", purchaseOrder);

		return "admin/edit-purchase-order";
	}

	@PostMapping("/edit-purchase-order")
	public String executeEditPurchaseOrder(@RequestBody PurchaseOrderRequest request) {

		System.out.println("Received request: " + request.toString());

		purchaseOrderService.update(request);

		return "redirect:/admin/data-orders";
	}

	@GetMapping("/edit-sale-order/{orderId}")
	public String EditSaleOrder(@PathVariable int orderId, Model model) {
		SalesOrder salesOrder = salesOrderService.getSalesOrderById(orderId);
		List<Product> products = productService.listAll();
		List<Customer> customers = customerService.getCustomers();

		if (salesOrder == null || salesOrder.getOrderDetails().isEmpty()) {
			return "redirect:/admin/error";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // Định dạng của bạn
		String formattedDate = sdf.format(new Date(salesOrder.getOrderDateCreate().getTime()));
		model.addAttribute("formattedDate", formattedDate);
		model.addAttribute("orderDetails", salesOrder.getOrderDetails());
		model.addAttribute("totalPrice", salesOrder.calculateTotalPrice());
		model.addAttribute("customers", customers);
		model.addAttribute("products", products);
		model.addAttribute("order", salesOrder);

		return "admin/edit-sale-order";
	}

	@PostMapping("/edit-sale-order")
	public String executeEditSaleOrder(@RequestBody SalesOrderRequest request) {
		System.out.println("Received request: " + request.toString());

		salesOrderService.update(request);

		return "redirect:/admin/data-orders";
	}

	@GetMapping("/edit-supplier/{supplierId}")
	public String EditSupplier(@PathVariable int supplierId, Model model) {
		Supplier supplier = supplierService.getSupplierById(supplierId);
		if (supplier == null) {
			return "redirect:/admin/error";
		}
		model.addAttribute("supplier", supplier);

		return "admin/edit-supplier";
	}

	@PostMapping("/edit-supplier")
	public String executeEditSupplier(@ModelAttribute Supplier supplier) {
		supplierService.update(supplier);

		return "redirect:/admin/data-suppliers";
	}

	@GetMapping("/delete-account")
	public String deleteAccount(@RequestParam Integer accountId, Model model) {

		accountService.deleteAccountById(accountId);

		return "redirect:/admin/data-accounts";
	}

	@GetMapping("/delete-category")
	public String deleteCategory(@RequestParam Integer categoryId, Model model, HttpServletResponse response) {
		try {
			categoryService.deleteCategoryById(categoryId);
			return "redirect:/admin/data-categories";
		} catch (Exception e) {
			model.addAttribute("error", "Error deleting category");
			return "redirect:/admin/data-categories";
		}
	}

	@GetMapping("/delete-order")
	public String deleteOrder(@RequestParam Integer orderId, Model model) {

		orderService.deleteOrderById(orderId);

		return "redirect:/admin/data-orders";
	}

	@GetMapping("/delete-product")
	public String deleteProduct(@RequestParam Integer productId, Model model) {

		productService.deleteProductById(productId);

		return "redirect:/admin/data-products";
	}

	@GetMapping("/delete-supplier")
	public String deleteSupplier(@RequestParam Integer supplierId, Model model) {

		supplierService.deleteSupplierById(supplierId);

		return "redirect:/admin/data-suppliers";
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

	@GetMapping("/**")
	public String handleInvalidUrl() {
		return "redirect:/admin/error";
	}
}