package com.fpoly.java6.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpoly.java6.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//	    boolean existsByBarcode(String barcode);  // Kiểm tra xem mã vạch đã tồn tại chưa

    // Bạn có thể thêm các phương thức tìm kiếm tùy chỉnh nếu cần
    // Ví dụ: Tìm sản phẩm theo tên
	Product findByName(String name);

    
    // Ví dụ: Tìm sản phẩm theo mã vạch

    
    // Hoặc sử dụng các truy vấn nâng cao với @Query
    // @Query("SELECT p FROM Product p WHERE p.brand.name = :brandName")
    // List<Product> findByBrandName(@Param("brandName") String brandName);
}
