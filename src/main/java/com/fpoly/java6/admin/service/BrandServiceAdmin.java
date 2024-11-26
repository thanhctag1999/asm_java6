package com.fpoly.java6.admin.service;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.BrandRepository;
import com.fpoly.java6.entities.Brand;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceAdmin {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
    public Brand findById(int id) {
        return brandRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Brand not found"));
    }
    
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

//    public boolean deleteBrand(Long id) {
//        Optional<Brand> product = brandRepository.findById(id);
//        if (product.isPresent()) {
//            // Xóa các biến thể liên quan trước khi xóa sản phẩm
//            brandRepository.deleteByBrandId(id);  // Đảm bảo rằng phương thức này được định nghĩa trong VariantRepository
//            return true;
//        }
//        return false;  // Nếu sản phẩm không tồn tại
//    }
    
    public boolean deleteBrand(int id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brandRepository.delete(brand);
            return true; // Trả về true nếu xóa thành công
        }
        return false; // Trả về false nếu không tìm thấy thương hiệu
    }


}
