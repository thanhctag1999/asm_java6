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
public class OrderRequestDto {
    private int accountId;
    private String name;
    private String email;
    private Date date;
    private String phone;
    private String fullAddress;
    private String description;
    private int paymentMethod;  // 1: Credit Card, 2: Other
    private int status;  // Trạng thái đơn hàng
    private int feeShip;
    private int totalPrice;
    private List<OrderDetailDto> orderDetails;	  
}


