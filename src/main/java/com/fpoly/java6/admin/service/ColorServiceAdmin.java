package com.fpoly.java6.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.admin.repository.ColorRepository;
import com.fpoly.java6.entities.Color;


@Service
public class ColorServiceAdmin {

    @Autowired
    private ColorRepository colorRepository;

    // Lấy tất cả màu sắc
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    // Tìm Color theo ID và trả về Optional<Color>
    public Optional<Color> findById(int colorId) {
        return colorRepository.findById(colorId);
    }

    // Tìm Color theo ID và trả về null nếu không tìm thấy
    public Color findByIdOrNull(int colorId) {
        return colorRepository.findById(colorId).orElse(null);
    }

    // Lưu hoặc cập nhật Color
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    // Xóa Color
    public boolean deleteColor(int colorId) {
        try {
            colorRepository.deleteById(colorId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
