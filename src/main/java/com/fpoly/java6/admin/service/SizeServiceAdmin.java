package com.fpoly.java6.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.SizeRepository;
import com.fpoly.java6.entities.Size;


@Service
public class SizeServiceAdmin {

    @Autowired
    private SizeRepository sizeRepository;

    // Lấy tất cả kích thước
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    // Tìm Size theo ID và trả về Optional<Size>
    public Optional<Size> findById(int sizeId) {
        return sizeRepository.findById(sizeId);
    }

    // Tìm Size theo ID và trả về null nếu không tìm thấy
    public Size findByIdOrNull(int sizeId) {
        return sizeRepository.findById(sizeId).orElse(null);
    }
    
    // Tạo hoặc lưu một kích cỡ mới
    public Size save(Size size) {
        return sizeRepository.save(size);
    }

    // Xóa kích cỡ
    public boolean deleteSize(int sizeId) {
        Optional<Size> size = sizeRepository.findById(sizeId);
        if (size.isPresent()) {
            sizeRepository.deleteById(sizeId);
            return true;
        }
        return false;
    }
}
