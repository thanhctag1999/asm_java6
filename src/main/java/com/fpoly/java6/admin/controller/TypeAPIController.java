package com.fpoly.java6.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fpoly.java6.admin.service.TypeServiceAdmin;
import com.fpoly.java6.entities.Type;

@RestController
@RequestMapping("/Admin")
public class TypeAPIController {

    @Autowired
    private TypeServiceAdmin typeService;

    // Thêm loại sản phẩm
    @PostMapping("/types/add")
    public ResponseEntity<?> addType(@RequestParam("typeName") String typeName) {
        // Kiểm tra tên loại sản phẩm không được rỗng và hợp lệ
        if (typeName == null || typeName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên loại sản phẩm không được để trống");
        }

        try {
            // Tạo đối tượng Type
            Type type = new Type();
            type.setName(typeName);

            // Lưu loại sản phẩm vào cơ sở dữ liệu
            Type savedType = typeService.save(type);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedType);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm loại sản phẩm: " + e.getMessage());
        }
    }

    // Cập nhật loại sản phẩm
    @PutMapping("/types/edit/{typeId}")
    public ResponseEntity<?> updateType(@PathVariable int typeId, @RequestParam("typeName") String typeName) {
        try {
            // Tìm loại sản phẩm theo ID
            Type existingType = typeService.findByIdOrNull(typeId);
            if (existingType == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loại sản phẩm không tồn tại");
            }

            // Cập nhật tên loại sản phẩm nếu có
            if (typeName != null && !typeName.trim().isEmpty()) {
                existingType.setName(typeName);
            }

            // Lưu loại sản phẩm đã cập nhật
            Type savedType = typeService.save(existingType);
            return ResponseEntity.ok(savedType);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật loại sản phẩm: " + e.getMessage());
        }
    }

    // Lấy thông tin loại sản phẩm
    @GetMapping(value = "/types/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Type> getType(@PathVariable int id) {
        Type type = typeService.findByIdOrNull(id);
        if (type != null) {
            return ResponseEntity.ok(type);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa loại sản phẩm
    @DeleteMapping("/types/delete/{typeId}")
    public ResponseEntity<?> deleteType(@PathVariable int typeId) {
        try {
            boolean isDeleted = typeService.deleteType(typeId);
            if (isDeleted) {
                return ResponseEntity.ok().body("Loại sản phẩm đã được xóa");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loại sản phẩm không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa loại sản phẩm: " + e.getMessage());
        }
    }
}
