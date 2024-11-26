package com.fpoly.java6.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.TypeRepository;
import com.fpoly.java6.entities.Type;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceAdmin {

    @Autowired
    private TypeRepository typeRepository;

    // Lấy tất cả các loại sản phẩm
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    // Tìm loại sản phẩm theo ID và ném ngoại lệ nếu không tìm thấy
    public Type findById(int id) {
        return typeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Type not found"));
    }

    // Lấy tất cả các loại sản phẩm
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    // Lưu loại sản phẩm mới hoặc cập nhật nếu tồn tại
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    // Tìm loại sản phẩm theo ID hoặc trả về null nếu không tìm thấy
    public Type findByIdOrNull(int id) {
        return typeRepository.findById(id).orElse(null);
    }

    // Xóa loại sản phẩm theo ID
    public boolean deleteType(int id) {
        try {
            typeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
