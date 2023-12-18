package com.example.ecommerce.entity.Account;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/*
 * Tài khoản (Class chung) được phân ra nhờ thuộc tính role được tạo bởi hibernate
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Table(name = "tbl_account")
public abstract class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private String fullName;
	private String password;
	private String address;
	private String email;
	@Column(name = "phone", length = 10)
	private String phone;
	@Column(name = "reset_password_token", length = 30)
	private String resetPasswordToken;

	public abstract String getRole();

	public Account() {
		super();
	}

	public Account(int accountId, String fullName, String password, String role, String address, String email,
			String phone, String resetPasswordToken) {
		super();
		this.accountId = accountId;
		this.fullName = fullName;
		this.password = password;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.resetPasswordToken = resetPasswordToken;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
}
