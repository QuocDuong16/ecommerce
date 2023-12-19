package com.example.ecommerce.data;

import java.util.ArrayList;

public class SalesOrderRequest {
	public int orderId;
	public String orderDateCreate;
	public int customerId;
	public ArrayList<OrderDetailRequest> orderDetails;
}
