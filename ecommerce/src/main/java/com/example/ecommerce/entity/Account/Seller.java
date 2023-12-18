package com.example.ecommerce.entity.Account;

import java.util.List;

import com.example.ecommerce.entity.Product.IndividualProduct;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

/*
 * Người bán
 * Kế thừa từ Tài khoản với role được gán là seller
 * Người bán có thể thêm sản phẩm để bán
 */
@Entity
@DiscriminatorValue("seller")
public class Seller extends Account {
	@OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
	private List<IndividualProduct> individualProducts;
	public Seller() {
		super();
	}

	public Seller(List<IndividualProduct> individualProducts) {
		super();
		this.individualProducts = individualProducts;
	}

	public Seller(int accountId, String fullName, String password, String role, String address, String email,
			String phone, String resetPasswordToken, List<IndividualProduct> individualProducts) {
		super(accountId, fullName, password, role, address, email, phone, resetPasswordToken);
		this.individualProducts = individualProducts;
	}

	public List<IndividualProduct> getIndividualProducts() {
		return individualProducts;
	}

	public void setIndividualProducts(List<IndividualProduct> individualProducts) {
		this.individualProducts = individualProducts;
	}

	@Override
	public String getRole() {
		return "seller";
	}
}
