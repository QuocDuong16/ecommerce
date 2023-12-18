package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Product.IndividualProduct;
import com.example.ecommerce.repository.IndividualProductRepository;

@Service
public class IndividualProductService {
	@Autowired
	private IndividualProductRepository individualProductRepository;
	
	public List<IndividualProduct> getIndividualProducts() {
		return individualProductRepository.findAll();
	}
}
