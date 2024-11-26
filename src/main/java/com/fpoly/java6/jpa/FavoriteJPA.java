package com.fpoly.java6.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Favorite;
import com.fpoly.java6.entities.Product;

import jakarta.transaction.Transactional;

public interface FavoriteJPA extends JpaRepository<Favorite, Integer> {
	List<Favorite> findByAccountId(int accountId);

	Favorite findByAccountIdAndProductId(int accountId, int productId);
	
	

	    @Transactional
	    void deleteByAccountIdAndProductId(int accountId, int productId);

//	void deleteByAccountIdAndProductId(int accountId, int productId);
	
	@Query("SELECT COUNT(f) > 0 FROM Favorite f WHERE f.account.id = :accountId AND f.product.id = :productId")
	boolean existsByAccountIdAndProductId(@Param("accountId") int accountId, @Param("productId") int productId);


	
}
