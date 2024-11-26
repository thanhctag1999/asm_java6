package com.fpoly.java6.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private int id;
    private int accountId;
    private int discountId; 
    private Date date;
    private int status; 
    private String description; 
    private int totalPrice;
    private int paymentMethod;
    private int fee;
    private String fullAddress;
    private List<OrderDetailDto> orderDetails;

    
    public String getStatusString() {
        switch (status) {
            case 1:
                return "Đang xử lý";
            case 2:
                return "Đang giao hàng";
            case 3:
                return "Giao hàng thành công";
            default:
                return "Không xác định";
        }
    }
    
    public boolean canDelete() {
        return status == 1; // Chỉ có thể xóa nếu trạng thái là "Đang xử lý"
    }
}
