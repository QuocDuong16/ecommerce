package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Product.EnterpriseProduct;
import com.example.ecommerce.repository.EnterpriseProductRepository;

@Service
public class EnterpriseProductService {
	@Autowired
	private EnterpriseProductRepository enterpriseProductRepository;
	
	public List<EnterpriseProduct> getEnterpriseProducts() {
		return enterpriseProductRepository.findAll();
	}
	
	public void update(EnterpriseProduct enterpriseProduct) {
		enterpriseProductRepository.save(enterpriseProduct);
	}

	public EnterpriseProduct getEnterpriseProductById(int id) {
		return enterpriseProductRepository.findById(id).orElse(null);
	}
}
