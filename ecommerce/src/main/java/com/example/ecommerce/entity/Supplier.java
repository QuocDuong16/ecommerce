package com.example.ecommerce.entity;

import java.util.List;

import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.entity.Product.EnterpriseProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * Nhà cung cấp
 */
@Entity
@Table(name = "tbl_supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    private String supplierName;
    private String address;
    private String email;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private List<PurchaseOrder> purchaseOrders;
    
    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
	private List<EnterpriseProduct> enterpriseProducts;

	public Supplier() {
		super();
	}

	public Supplier(int supplierId, String supplierName, String address, String email,
			List<PurchaseOrder> purchaseOrders, List<EnterpriseProduct> enterpriseProducts) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.address = address;
		this.email = email;
		this.purchaseOrders = purchaseOrders;
		this.enterpriseProducts = enterpriseProducts;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public List<EnterpriseProduct> getEnterpriseProducts() {
		return enterpriseProducts;
	}

	public void setEnterpriseProducts(List<EnterpriseProduct> enterpriseProducts) {
		this.enterpriseProducts = enterpriseProducts;
	}
}
