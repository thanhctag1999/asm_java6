package com.fpoly.java6.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    Account findByEmail(String email);
    Account findByName(String name);
    Account findByPhone(String phone);
//    Account findByUserCode(String userCode);


}
