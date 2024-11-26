package com.fpoly.java6.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Address;
import com.fpoly.java6.entities.Cart_Detail;
import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.model.dto.AccountDTO;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.model.dto.ApiResponse;
import com.fpoly.java6.model.dto.CheckoutRequest;
import com.fpoly.java6.model.dto.OrderDto;
import com.fpoly.java6.model.dto.OrderRequestDto;
import com.fpoly.java6.model.dto.OrderResponseDto;
import com.fpoly.java6.model.mapper.OrderDetailMapper;
import com.fpoly.java6.model.mapper.OrderMapper;
import com.fpoly.java6.service.AccountService;
import com.fpoly.java6.service.OrderService;
import com.fpoly.java6.service.PaymentService;
import com.fpoly.java6.service.VariantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private VariantService variantService;

	@PostMapping("/place-order")
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequest) {
		try {
			Order order = orderService.placeOrder(orderRequest);
			Map<String, Object> response = new HashMap<>();
			response.put("orderId", order.getId());
			response.put("success", true);
			response.put("message", "Order placed successfully.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();	
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to place the order.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
	
	@PostMapping("/{accountId}/checkout")
	public ResponseEntity<Order> checkoutCart(@PathVariable int accountId, @RequestBody OrderRequestDto orderRequestDto) {
	    Order order = orderService.buyAllItemsInCart(accountId, orderRequestDto);
	    return ResponseEntity.ok(order);
	}


	@GetMapping("/account/{accountId}/latest")
	public List<OrderDto> getLatestOrdersByAccountId(@PathVariable int accountId) {
		return orderService.getOrdersByAccountId(accountId);
	}

	//xóa đơn hàng
	@PutMapping("/{id}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable int id) {
	    try {
	        // Tìm đơn hàng theo id
	        Order order = orderService.findById(id);
	        
	        // Kiểm tra trạng thái đơn hàng
	        if (order.getStatus() == 1) {
	            // Set trạng thái đơn hàng thành '0' (hủy đơn hàng)
	            order.setStatus(0); 
	            orderService.save(order); // Lưu lại trạng thái mới

	            return ResponseEntity.ok("Đơn hàng đã được hủy thành công!");
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body("Không thể hủy đơn hàng vì trạng thái không phải là 'Đang xử lý'.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
	    }
	}



}