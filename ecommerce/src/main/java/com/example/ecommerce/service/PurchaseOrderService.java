package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Order.PurchaseOrder;
import com.example.ecommerce.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	public List<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrderRepository.findAll();
	}
}
