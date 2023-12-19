package com.example.ecommerce.entity.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.List;

import com.example.ecommerce.entity.OrderDetail;

/*
 * Hóa đơn (Class chung) được phân ra nhờ thuộc tính order_type được tạo bởi hibernate
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "tbl_order")
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "order_date_create", nullable = false)
    private Timestamp orderDateCreate;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    @PrePersist
    protected void onCreate() {
        if (orderDateCreate == null) {
            orderDateCreate = new Timestamp(System.currentTimeMillis());
        }
    }

	public Order() {
		super();
	}

	public Order(int orderId, Timestamp orderDateCreate, List<OrderDetail> orderDetails) {
		super();
		this.orderId = orderId;
		this.orderDateCreate = orderDateCreate;
		this.orderDetails = orderDetails;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Timestamp getOrderDateCreate() {
		return orderDateCreate;
	}

	public void setOrderDateCreate(Timestamp orderDateCreate) {
		this.orderDateCreate = orderDateCreate;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public int calculateTotalPrice() {
        return getOrderDetails().stream()
                .mapToInt(OrderDetail::getTotalPrice)
                .sum();
    }
}
