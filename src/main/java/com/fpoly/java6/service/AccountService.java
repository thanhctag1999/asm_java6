package com.fpoly.java6.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.jpa.AccountJPA;

@Service
public class AccountService {
	@Autowired
	private AccountJPA accountJPA;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<Account> findByEmail(String email) {
		return accountJPA.findByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return accountJPA.existsByEmail(email);
	}

	public void save(Account account) {
		// Encode the password before saving
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountJPA.save(account);
	}

	public void changePassword(String email, String newPassword) {
		Account account = accountJPA.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Account not found"));

		// Mã hóa mật khẩu mới trước khi lưu vào cơ sở dữ liệu
		String hashedPassword = passwordEncoder.encode(newPassword);
		account.setPassword(hashedPassword);
		accountJPA.save(account);
	}

	public Account updateAccount(int accountId, Account updatedAccount) {
	    // Check if the email already exists for another account
	    if (accountJPA.existsByEmail(updatedAccount.getEmail())
	            && !accountJPA.findById(accountId).get().getEmail().equals(updatedAccount.getEmail())) {
	        throw new RuntimeException("Email đã được sử dụng bởi tài khoản khác!");
	    }

	    // Find the account and update its attributes
	    return accountJPA.findById(accountId).map(account -> {
	        account.setName(updatedAccount.getName());
	        account.setPhone(updatedAccount.getPhone());
	        account.setGender(updatedAccount.isGender());
	        account.setImage(updatedAccount.getImage());
	        return accountJPA.save(account); // Save and return updated account
	    }).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + accountId));
	}

	public Account findById(int accountId) {
	    // Assuming you have a repository to find Account by its ID
	    return accountJPA.findById(accountId).orElse(null);  // Returns null if not found
	}

}
