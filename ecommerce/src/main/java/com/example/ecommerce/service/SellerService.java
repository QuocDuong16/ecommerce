package com.example.ecommerce.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.data.OrderInfo;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Customer;
import com.example.ecommerce.entity.Account.Seller;
import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.repository.SellerRepository;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	
	public List<Seller> getSellers() {
		return sellerRepository.findAll();
	}
	
	public Seller getSellerById(int id) {
		return sellerRepository.findById(id).orElse(null);
	}
	
	public void update(Seller seller) {
		sellerRepository.save(seller);
	}

	public List<OrderInfo> findTopFiveSellers() {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

		List<Seller> sellers = sellerRepository.findByIndividualProductsIsNotNull();

		for (Seller seller : sellers) {
			int quantity = 0;
			for (IndividualProduct individualProduct : seller.getIndividualProducts()) {
				for (OrderDetail orderDetail : individualProduct.getOrderDetails()) {
					quantity += orderDetail.getQuantity();
				}
			}
			if (quantity == 0)
				continue;
			orderInfos.add(new OrderInfo(seller.getFullName(), quantity));
		}

		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));
		return orderInfos.size() < 5 ? orderInfos : orderInfos.subList(0, 5);
	}

	public List<OrderInfo> findTopFiveSellers(LocalDate startDate, LocalDate endDate) {
		List<OrderInfo> orderInfos = new ArrayList<>();

		List<Seller> sellers = sellerRepository.findByIndividualProductsIsNotNull();

		for (Seller seller : sellers) {
			int quantity = 0;
			for (IndividualProduct individualProduct : seller.getIndividualProducts()) {
				for (OrderDetail orderDetail : individualProduct.getOrderDetails()) {
					Timestamp orderDateCreate = orderDetail.getOrder().getOrderDateCreate();
					LocalDate localDate = orderDateCreate.toLocalDateTime().toLocalDate();
					if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
						quantity += orderDetail.getQuantity();
					}
				}
			}
			if (quantity == 0)
				continue;
			orderInfos.add(new OrderInfo(seller.getFullName(), quantity));
		}

		Collections.sort(orderInfos, (p1, p2) -> Integer.compare(p2.quantity(), p1.quantity()));
		return orderInfos.size() < 5 ? orderInfos : orderInfos.subList(0, 5);
	}
}
