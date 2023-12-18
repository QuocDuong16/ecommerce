package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
