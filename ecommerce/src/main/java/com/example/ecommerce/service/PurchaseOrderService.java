package com.example.ecommerce.service;

import com.example.ecommerce.entity.OrderDetail;

import java.sql.Timestamp;
import java.util.List;

import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	@Autowired
	private SupplierService supplierService;

	public void AddPurchaseOrder(int supplierId, String date,List<OrderDetail> orderDetailsRow) {
		PurchaseOrder  purchaseOrder = new PurchaseOrder();
		Supplier supplier = supplierService.findById(supplierId);
		purchaseOrder.setSupplier(supplier);
		Timestamp orderDateCreate = Timestamp.valueOf(date);
	    purchaseOrder.setOrderDateCreate(orderDateCreate);
		purchaseOrder.setOrderDetails(orderDetailsRow);
		System.out.println("1");
		purchaseOrderRepository.save(purchaseOrder);
	}
}
