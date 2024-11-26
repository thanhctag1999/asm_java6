package com.fpoly.java6.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.java6.entities.Brand;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.model.dto.SearchDto;

@Repository
public interface ProductJPA extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
	List<Product> searchByName(@Param("name") String name);
	@Query("SELECT p FROM Product p JOIN p.variants v WHERE v.size.id = :sizeId")
	List<Product> findBySizeId(@Param("sizeId") int sizeId);
	@Query("SELECT p FROM Product p JOIN p.variants v WHERE v.color.id = :colorId")
	List<Product> findByColorId(@Param("colorId") String colorId);
	 
	List<Product> findByTypeName(String typeName);
	List<Product> findByBrandName(String brandName);

	Page<Product> findAll(Pageable pageable);
}
