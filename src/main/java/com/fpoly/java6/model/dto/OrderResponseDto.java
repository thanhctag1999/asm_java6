package com.fpoly.java6.model.dto;

import com.fpoly.java6.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
	private int orderId; // Mã đơn hàng
	private boolean success;
	private String message; // Thông điệp phản hồi
//	 
	
	private OrderResponseDto convertToOrderResponse(Order order) {
	    // Kiểm tra xem đơn hàng có tồn tại không và chuyển đổi sang DTO
	    if (order != null) {
	        return new OrderResponseDto(order.getId(), true, "Đặt hàng thành công!");
	    } else {
	        return new OrderResponseDto(0, false, "Đã xảy ra lỗi khi đặt hàng!");
	    }
	}


}
