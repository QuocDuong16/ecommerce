package com.example.ecommerce.service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.data.OrderDetailRequest;
import com.example.ecommerce.data.PurchaseOrderRequest;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.entity.Key.OrderDetailKey;
import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private SupplierService supplierService;
	
	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrderRepository.findAll();
	}
	
	public PurchaseOrder getPurchaseOrderById(int id) {
		return purchaseOrderRepository.findById(id).orElse(null);
	}
	
	public void update(PurchaseOrderRequest request) {
		PurchaseOrder purchaseOrder = getPurchaseOrderById(request.orderId);
		Supplier supplier = supplierService.getSupplierById(request.supplierId);
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        try {
            Date date = sdfInput.parse(request.orderDateCreate);

            long timestamp = date.getTime();
            purchaseOrder.setOrderDateCreate(new Timestamp(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
		purchaseOrder.setSupplier(supplier);
		for (OrderDetail orderDetail: purchaseOrder.getOrderDetails()) {
			for (OrderDetailRequest o: request.orderDetails) {
				if (orderDetail.getId().equals(new OrderDetailKey(Integer.valueOf(o.orderId), Integer.valueOf(o.productId)))) {
					orderDetail.setQuantity(Integer.valueOf(o.quantity));
				}
			}
		}
		
		purchaseOrderRepository.save(purchaseOrder);
	}
}
