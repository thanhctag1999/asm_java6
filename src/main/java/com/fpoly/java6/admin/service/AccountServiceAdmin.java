package com.fpoly.java6.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.AccountRepository;
import com.fpoly.java6.entities.Account;

import java.util.List;
import java.util.Optional;

@Service

public class AccountServiceAdmin {

    @Autowired
    private AccountRepository accountRepository;

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    public Account findById(int id) {
        return accountRepository.findById(id).orElse(null);
    }
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    
    public Account findByPhone(String phone) {
        return accountRepository.findByPhone(phone);
    }

//    public Account findByUserCode(String userCode) {
//        return accountRepository.findByUserCode(userCode);
//    }


    public Account findByUsername(String name) {
        return accountRepository.findByName(name);
    }

    public boolean deleteAccount(int id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
    
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

}
