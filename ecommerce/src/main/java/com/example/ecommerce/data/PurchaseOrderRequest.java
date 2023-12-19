package com.example.ecommerce.data;

import java.util.ArrayList;
import java.util.List;

import com.example.ecommerce.entity.OrderDetail;

public class PurchaseOrderRequest {
	public int orderId;
	public String orderDateCreate;
	public int supplierId;
	public ArrayList<OrderDetailRequest> orderDetails;
}

