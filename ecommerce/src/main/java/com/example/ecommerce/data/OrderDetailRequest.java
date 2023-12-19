package com.example.ecommerce.data;

public class OrderDetailRequest {
	public String orderId;
	public String productId;
	public String quantity;
	
    public OrderDetailRequest(String orderId, String productId, String quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }
}
