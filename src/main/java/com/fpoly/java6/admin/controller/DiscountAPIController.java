package com.fpoly.java6.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fpoly.java6.admin.service.DiscountServiceAdmin;
import com.fpoly.java6.entities.Discount;

@RestController
@RequestMapping("/Admin/discounts")
public class DiscountAPIController {

    @Autowired
    private DiscountServiceAdmin discountService;


    // Thêm mã giảm giá
    @PostMapping("/add")
    public ResponseEntity<?> addDiscount(@RequestBody Discount discount) {
        if (discount.getCode() == null || discount.getCode().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không được để trống");
        }
        
        if (discount.getDiscount_value() == null || discount.getDiscount_value() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá trị giảm giá phải lớn hơn 0");
        }
        if (discount.getStart_date() == null || discount.getEnd_date() == null || 
            discount.getStart_date().after(discount.getEnd_date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày bắt đầu hoặc ngày kết thúc không hợp lệ");
        }
        if (discount.getQuantity() == null || discount.getQuantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng phải lớn hơn 0");
        }

        if (discount.getCode() == null || discount.getCode().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không được để trống");
        }

        if (discount.getDiscount_value() != null && discount.getDiscount_value() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá trị giảm giá phải lớn hơn 0");
        }
        if (discount.getStart_date() != null && discount.getEnd_date() != null && 
            discount.getStart_date().after(discount.getEnd_date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày bắt đầu phải trước ngày kết thúc");
        }
        if (discount.getQuantity() != null && discount.getQuantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng phải lớn hơn 0");
        }

        try {
            // Lưu mã giảm giá vào cơ sở dữ liệu
            Discount savedDiscount = discountService.save(discount);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDiscount);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi thêm mã giảm giá: " + e.getMessage());
        }
    }

    // Cập nhật mã giảm giá
//    @PutMapping("/edit/{discountId}")
//    public ResponseEntity<?> updateDiscount(@PathVariable Integer discountId, @RequestBody Discount discount) {
//        try {
//            // Tìm mã giảm giá theo ID
//            Discount existingDiscount = discountService.findById(discountId);
//            if (existingDiscount == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã giảm giá không tồn tại");
//            }
//            
//            if (discount.getDiscountValue() == null || discount.getDiscountValue() <= 0) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá trị giảm giá phải lớn hơn 0");
//            }
//            if (discount.getStartDate() == null || discount.getEndDate() == null || 
//                discount.getStartDate().after(discount.getEndDate())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày bắt đầu hoặc ngày kết thúc không hợp lệ");
//            }
//            if (discount.getQuantity() == null || discount.getQuantity() <= 0) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng phải lớn hơn 0");
//            }
//
//            if (discount.getDiscountCode() == null || discount.getDiscountCode().trim().isEmpty()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá không được để trống");
//            }
//
//            if (discount.getDiscountValue() != null && discount.getDiscountValue() <= 0) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá trị giảm giá phải lớn hơn 0");
//            }
//            if (discount.getStartDate() != null && discount.getEndDate() != null && 
//                discount.getStartDate().after(discount.getEndDate())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày bắt đầu phải trước ngày kết thúc");
//            }
//            if (discount.getQuantity() != null && discount.getQuantity() <= 0) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng phải lớn hơn 0");
//            }
//
//            // Cập nhật thông tin mã giảm giá
//            if (discount.getDiscountCode() != null) existingDiscount.setDiscountCode(discount.getDiscountCode());
//            if (discount.getDiscountValue() != null) existingDiscount.setDiscountValue(discount.getDiscountValue());
//            if (discount.getStartDate() != null) existingDiscount.setStartDate(discount.getStartDate());
//            if (discount.getEndDate() != null) existingDiscount.setEndDate(discount.getEndDate());
//            if (discount.getQuantity() != null) existingDiscount.setQuantity(discount.getQuantity());
//            if (discount.getDescription() != null) existingDiscount.setDescription(discount.getDescription());
//            if (discount.getStatus() != null) existingDiscount.setStatus(discount.getStatus());
//
//            // Lưu mã giảm giá đã cập nhật
//            Discount savedDiscount = discountService.save(existingDiscount);
//            return ResponseEntity.ok(savedDiscount);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
//        }
//    }
    
 // Cập nhật mã giảm giá
    @PutMapping("/edit/{discountId}")
    public ResponseEntity<?> updateDiscount(@PathVariable int discountId, @RequestBody Discount discount) {
        try {
            // Tìm mã giảm giá theo ID
            Discount existingDiscount = discountService.findById(discountId);
            if (existingDiscount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã giảm giá không tồn tại");
            }

            // Kiểm tra giá trị discountValue
            if (discount.getDiscount_value() == null || discount.getDiscount_value() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá trị giảm giá phải lớn hơn 0");
            }

            // Kiểm tra logic ngày tháng
            java.util.Date startDate = discount.getStart_date() != null ? discount.getStart_date() : existingDiscount.getStart_date();
            java.util.Date endDate = discount.getEnd_date() != null ? discount.getEnd_date() : existingDiscount.getEnd_date();
            if (startDate.after(endDate)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày bắt đầu phải trước ngày kết thúc");
            }

            // Kiểm tra số lượng
            if (discount.getQuantity() != null && discount.getQuantity() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng phải lớn hơn 0");
            }

            // Cập nhật thông tin mã giảm giá
            if (discount.getCode() != null) existingDiscount.setCode(discount.getCode());
            existingDiscount.setDiscount_value(discount.getDiscount_value());
            existingDiscount.setStart_date(startDate); // Sử dụng ngày đã kiểm tra ở trên
            existingDiscount.setEnd_date(endDate);     // Sử dụng ngày đã kiểm tra ở trên
            if (discount.getQuantity() != null) existingDiscount.setQuantity(discount.getQuantity());
            if (discount.getDescription() != null) existingDiscount.setDescription(discount.getDescription());
            if (discount.getStatus() != null) existingDiscount.setStatus(discount.getStatus());

            // Lưu mã giảm giá đã cập nhật
            Discount savedDiscount = discountService.save(existingDiscount);
            return ResponseEntity.ok(savedDiscount);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }


//    // Áp dụng mã giảm giá cho sản phẩm
//    @PostMapping("/apply/{discountId}/{productId}")
//    public ResponseEntity<?> applyDiscountToProduct(@PathVariable Integer discountId, @PathVariable Long productId) {
//        try {
//            Discount discount = discountService.findById(discountId);
//
//            if (discount == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã giảm giá không tồn tại");
//            }
//
//            // Kiểm tra xem discount đã hết hạn chưa
//            if (discount.getEndDate().before(new java.util.Date())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết hạn");
//            }
//
//
//            return ResponseEntity.ok("Mã giảm giá đã được áp dụng cho sản phẩm");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi áp dụng mã giảm giá: " + e.getMessage());
//        }
//    }

    // Lấy thông tin mã giảm giá
    @GetMapping(value = "/{discountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Discount> getDiscount(@PathVariable int discountId) {
        Discount discount = discountService.findById(discountId);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa mã giảm giá
    @DeleteMapping("/delete/{discountId}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Integer discountId) {
        try {
            boolean isDeleted = discountService.deleteDiscount(discountId);
            if (isDeleted) {
                return ResponseEntity.ok().body("Mã giảm giá đã được xóa");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã giảm giá không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa mã giảm giá: " + e.getMessage());
        }
    }
    
 

}
