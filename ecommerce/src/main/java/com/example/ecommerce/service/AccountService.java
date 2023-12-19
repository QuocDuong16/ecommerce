package com.example.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Account.Account;
import com.example.ecommerce.repository.AccountRepository;

@Service
public class AccountService implements AccountServiceInterface {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public Account createAccount(Account customer) {

		customer.setPassword(passwordEncode.encode(customer.getPassword()));

		return accountRepository.save(customer);
	}

	@Override
	public boolean checkEmail(String email) {

		return accountRepository.existsByEmail(email);
	}

	@Override
	public void storeResetToken(String email, String token) {
		Optional<Account> optionalAccount = accountRepository.findByEmail(email);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			account.setResetPasswordToken(token);
			accountRepository.save(account);
		} else {
			// Handle the case where the account with the given email is not found
		}
	}

	@Override
	public boolean isResetTokenValid(String token) {
		// TODO: Implement logic to validate the reset token
		return true; // Placeholder, replace with actual logic
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// Validate the reset token
		Optional<Account> optionalAccount = accountRepository.findByResetPasswordToken(token);
		if (optionalAccount.isPresent()) {
			// Reset password logic
			Account account = optionalAccount.get();
			account.setPassword(passwordEncode.encode(newPassword));
			account.setResetPasswordToken(null);
			accountRepository.save(account); // Save the updated account
		} else {
			throw new UserNotFoundException("User not found for the given reset token");
		}
	}

	public Account getAccountById(int id) {
		return accountRepository.findById(id).orElse(null);
	}

	public void updateAccount(Account updatedAccount) {
		if (accountRepository.existsById(updatedAccount.getAccountId())) {
			accountRepository.save(updatedAccount);
		}
	}

	public void deleteAccountById(Integer accountId) {
		// Kiểm tra xem tài khoản có tồn tại hay không
		Optional<Account> optionalAdmin = accountRepository.findById(accountId);
		if (optionalAdmin.isPresent()) {
			// Nếu tồn tại, thực hiện xóa
			Account admin = optionalAdmin.get();
			accountRepository.delete(admin);
		} else {
			// Nếu không tồn tại, có thể thực hiện xử lý thông báo hoặc ném một exception
			// tùy thuộc vào yêu cầu của bạn
			// Ví dụ: throw new NotFoundException("Account not found with ID: " +
			// accountId);
		}
	}

}