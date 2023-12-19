package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product.Product;
import com.example.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    public void addToCategory(String categoryName) {
    	Category category = new Category();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
	public Category findById(int categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
	}

}
