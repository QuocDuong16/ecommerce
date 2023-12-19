package com.example.ecommerce.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.data.ImageUtils;
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
import com.example.ecommerce.service.ProductService;
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
	        // Xử lý khi không tìm thấy sản phẩm
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

	@GetMapping("/edit-purchase-order/{purchaseOrderId}")
	public String EditPurchaseOrder(@PathVariable int purchaseOrderId, Model model) {
		
		return "admin/edit-purchase-order";
	}

	@GetMapping("/edit-sale-order/{saleOrderId}")
	public String EditSaleOrder(@PathVariable int saleOrderId, Model model) {
		return "admin/edit-sale-order";
	}

	@GetMapping("/edit-supplier/{supplierId}")
	public String EditSupplier(@PathVariable int supplierId, Model model) {
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
	
    @GetMapping("/**")
    public String handleInvalidUrl() {
        return "redirect:/admin/error";
    }
}