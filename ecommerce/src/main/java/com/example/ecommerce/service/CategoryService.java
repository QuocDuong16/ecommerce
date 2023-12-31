package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(int id) {
		return categoryRepository.findById(id).orElse(null);
	}

	public void update(Category category) {
		categoryRepository.save(category);
	}

	public void addToCategory(String categoryName) {
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryRepository.save(category);
	}

	public void deleteCategoryById(Integer categoryId) {
		// Kiểm tra xem tài khoản có tồn tại hay không
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isPresent()) {
			// Nếu tồn tại, thực hiện xóa
			Category category = optionalCategory.get();
			categoryRepository.delete(category);
		} else {

		}
	}
}
