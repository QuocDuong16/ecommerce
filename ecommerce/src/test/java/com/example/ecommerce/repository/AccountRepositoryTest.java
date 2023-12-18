package com.example.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce.entity.Account.Account;

@SpringBootTest
class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	public void findAllShowRole() {
		List<Account> accounts = accountRepository.findAll();
		for (Account account : accounts) {
			System.out.println(account.getRole());
		}
	}

}
