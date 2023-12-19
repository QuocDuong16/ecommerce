package com.example.ecommerce.service;

import com.example.ecommerce.entity.Supplier;
import com.example.ecommerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	public Supplier createSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	public void update(Supplier supplier) {
		supplierRepository.save(supplier);
	}

	public void deleteSupplierById(Integer accountId) {
		// Kiểm tra xem tài khoản có tồn tại hay không
		Optional<Supplier> optionalAdmin = supplierRepository.findById(accountId);
		if (optionalAdmin.isPresent()) {
			// Nếu tồn tại, thực hiện xóa
			Supplier admin = optionalAdmin.get();
			supplierRepository.delete(admin);
		} else {
			// Nếu không tồn tại, có thể thực hiện xử lý thông báo hoặc ném một exception
			// tùy thuộc vào yêu cầu của bạn
			// Ví dụ: throw new NotFoundException("Account not found with ID: " +
			// accountId);
		}
	}

	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAll();
	}

	public Supplier getSupplierById(int id) {
		return supplierRepository.findById(id).orElse(null);
	}
}
