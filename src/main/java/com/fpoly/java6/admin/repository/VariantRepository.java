package com.fpoly.java6.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.java6.entities.Variant;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> { 

    // Corrected parameter name in @Param to match query
    @Modifying
    @Transactional
    @Query("DELETE FROM Variant v WHERE v.product.id = :product_id")
    void deleteByProductId(@Param("product_id") int product_id);

    // Find variants by product ID
    List<Variant> findByProduct_Id(int id);

    // Find variant by Variant ID
//    Variant findByVariantId(int id);
    
    Variant findById(int id);

    // Corrected query to match fields: color and size
    @Query("SELECT v FROM Variant v WHERE v.color.color = :color AND v.size.size = :size")
    List<Variant> findByColor_ColorAndSize_Size(@Param("color") String color, @Param("size") int size);

    // Find variants by product ID
    @Query("SELECT v FROM Variant v WHERE v.product.id = :id")
    List<Variant> findVariantsByProductId(@Param("id") int id);

    // Check if variant exists with specific product, color, and size
    boolean existsByProduct_IdAndColor_IdAndSize_Id(int product_id, int color_id, int size_id);
}
