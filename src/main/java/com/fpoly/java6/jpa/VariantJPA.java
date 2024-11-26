package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Variant;


public interface VariantJPA extends JpaRepository<Variant, Integer>{
	List<Variant> findByProductId(int productId);
	
	List<Variant> findBySizeId(int sizeId);
	@Query("SELECT v FROM Variant v WHERE v.price BETWEEN :minPrice AND :maxPrice")
	List<Variant> findByPrice(@Param("minPrice") int minPrice, 
	                              @Param("maxPrice") int maxPrice, 
	                              Sort sort);
}
