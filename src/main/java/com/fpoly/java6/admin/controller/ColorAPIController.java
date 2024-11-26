package com.fpoly.java6.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fpoly.java6.admin.service.ColorServiceAdmin;
import com.fpoly.java6.entities.Color;


@RestController
@RequestMapping("/Admin")
public class ColorAPIController {

    @Autowired
    private ColorServiceAdmin colorService;

    @PostMapping("/colors/add")
    public ResponseEntity<?> addColor(@RequestParam("colorName") String colorName) {
        // Kiểm tra tên màu sắc không được rỗng và hợp lệ
        if (colorName == null || colorName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên màu sắc không được để trống");
        }

        try {
            // Tạo đối tượng Color
            Color color = new Color();
            color.setColor(colorName);

            // Lưu màu sắc vào cơ sở dữ liệu
            Color savedColor = colorService.save(color);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedColor);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm màu sắc: " + e.getMessage());
        }
    }

    @PutMapping("/colors/edit/{colorId}")
    public ResponseEntity<?> updateColor(@PathVariable int colorId, @RequestParam("colorName") String colorName) {
        try {
            // Tìm màu sắc theo ID
            Color existingColor = colorService.findByIdOrNull(colorId);
            if (existingColor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Màu sắc không tồn tại");
            }

            // Cập nhật tên màu sắc nếu có
            if (colorName != null && !colorName.trim().isEmpty()) {
                existingColor.setColor(colorName);
            }

            // Lưu màu sắc đã cập nhật
            Color savedColor = colorService.save(existingColor);
            return ResponseEntity.ok(savedColor);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật màu sắc: " + e.getMessage());
        }
    }

    @GetMapping(value = "/colors/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Color> getColor(@PathVariable int id) {
        Color color = colorService.findByIdOrNull(id);
        if (color != null) {
            return ResponseEntity.ok(color);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/colors/delete/{colorId}")
    public ResponseEntity<?> deleteColor(@PathVariable int colorId) {
        try {
            boolean isDeleted = colorService.deleteColor(colorId);
            if (isDeleted) {
                return ResponseEntity.ok().body("Màu sắc đã được xóa");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Màu sắc không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa màu sắc: " + e.getMessage());
        }
    }
}
