package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.Cart_Detail;

import com.fpoly.java6.entities.Variant;

public interface CartItemJPA extends JpaRepository<Cart_Detail, Integer> {
	List<Cart_Detail> findByAccountId(int accountId);

	Cart_Detail findByVariant(Variant variant);
}
