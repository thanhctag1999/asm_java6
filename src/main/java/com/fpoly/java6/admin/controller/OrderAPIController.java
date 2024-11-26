package com.fpoly.java6.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fpoly.java6.admin.service.OrderDetailsServiceAdmin;
import com.fpoly.java6.admin.service.OrderServiceAdmin;
import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Admin/orders")
public class OrderAPIController {

    @Autowired
    private OrderServiceAdmin orderService;

    @Autowired
    private OrderDetailsServiceAdmin orderDetailsService;
    
    
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<Order_Detail>> getOrderDetails(@PathVariable int orderId) {
        List<Order_Detail> details = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId, @RequestBody Map<String, String> request) {
        try {
            // Lấy và kiểm tra giá trị status từ request
            String statusString = request.get("status");
            if (statusString == null || statusString.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trạng thái không được để trống");
            }

            // Chuyển đổi status sang kiểu int
            int status;
            try {
                status = Integer.parseInt(statusString);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trạng thái phải là số nguyên hợp lệ");
            }

            // Gọi service để cập nhật trạng thái đơn hàng
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok().build();

        } catch (RuntimeException e) {
            // Xử lý lỗi không tìm thấy đơn hàng hoặc lỗi khác
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

}
