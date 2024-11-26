package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Address;

public interface AddressJPA extends JpaRepository<Address, Integer>{
    List<Address> findByAccountId(Integer accountId);
    

}
