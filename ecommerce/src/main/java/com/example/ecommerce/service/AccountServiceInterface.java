package com.example.ecommerce.service;

import com.example.ecommerce.entity.Account.Account;

public interface AccountServiceInterface {

	Account createAccount(Account customer);

	boolean checkEmail(String email);

	void storeResetToken(String email, String token);

	boolean isResetTokenValid(String token);

	void resetPassword(String token, String newPassword);
}
