package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Product.EnterpriseProduct;

@Repository
public interface EnterpriseProductRepository extends JpaRepository<EnterpriseProduct, Integer> {

}
