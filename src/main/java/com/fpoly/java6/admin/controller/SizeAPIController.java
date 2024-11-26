package com.fpoly.java6.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fpoly.java6.admin.service.SizeServiceAdmin;
import com.fpoly.java6.entities.Size;

@RestController
@RequestMapping("/Admin")
public class SizeAPIController {

    @Autowired
    private SizeServiceAdmin sizeService;

    // Thêm kích cỡ
    @PostMapping("/sizes/add")
    public ResponseEntity<?> addSize(@RequestParam("sizeName") Integer sizeName) {
        // Kiểm tra tên kích cỡ không được rỗng và hợp lệ
        if (sizeName == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("kích cỡ không được để trống");
        }

        try {
            // Tạo đối tượng Size
            Size size = new Size();
            size.setSize(sizeName);

            // Lưu kích cỡ vào cơ sở dữ liệu
            Size savedSize = sizeService.save(size);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSize);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm kích cỡ: " + e.getMessage());
        }
    }

    // Cập nhật kích cỡ
    @PutMapping("/sizes/edit/{sizeId}")
    public ResponseEntity<?> updateSize(@PathVariable int sizeId, @RequestParam("sizeName") Integer sizeName) {
        try {
            // Tìm kích cỡ theo ID
            Size existingSize = sizeService.findByIdOrNull(sizeId);
            if (existingSize == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kích cỡ không tồn tại");
            }

            // Cập nhật tên kích cỡ nếu có
            if (sizeName != null) {
                existingSize.setSize(sizeId);
            }

            // Lưu kích cỡ đã cập nhật
            Size savedSize = sizeService.save(existingSize);
            return ResponseEntity.ok(savedSize);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật kích cỡ: " + e.getMessage());
        }
    }

    // Lấy thông tin kích cỡ
    @GetMapping(value = "/sizes/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Size> getSize(@PathVariable int id) {
        Size size = sizeService.findByIdOrNull(id);
        if (size != null) {
            return ResponseEntity.ok(size);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa kích cỡ
    @DeleteMapping("/sizes/delete/{sizeId}")
    public ResponseEntity<?> deleteSize(@PathVariable int sizeId) {
        try {
            boolean isDeleted = sizeService.deleteSize(sizeId);
            if (isDeleted) {
                return ResponseEntity.ok().body("Kích cỡ đã được xóa");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kích cỡ không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa kích cỡ: " + e.getMessage());
        }
    }
}
