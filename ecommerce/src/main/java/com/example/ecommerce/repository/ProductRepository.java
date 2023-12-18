package com.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findByOrderDetailsIsNotNull();

	@Query("SELECT p FROM Product p WHERE p.id = :productId")
	public Optional<Product> findById(@Param("productId") int productId);

	List<Product> findByProductNameContainingIgnoreCase(String name);

	List<Product> findByColorIgnoreCase(String color);

	List<Product> findByProductPriceBetween(float minPrice, float maxPrice);
}
