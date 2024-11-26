package com.fpoly.java6.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;
import com.fpoly.java6.jpa.VariantJPA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    
    private int id; 
    private int orderId;
    private int variantId;
    private int quantity; 
    private int price; 


    // Static method to convert Order_Detail entity to OrderDetailDto
    public static OrderDetailDto toOrderDetailDTO(Order_Detail orderDetail) {
        return new OrderDetailDto(
            orderDetail.getId(),
            orderDetail.getOrder().getId(),
            orderDetail.getVariant().getId(),
            orderDetail.getQuantity(),
            orderDetail.getPrice());
    }
    
 



    // Static method to convert Order entity to OrderDto
    public static OrderDto toOrderDto(Order order) {
        // Convert Order_Detail entities to OrderDetailDto
        List<OrderDetailDto> orderDetailDtos = order.getOrder_Details().stream()
            .map(OrderDetailDto::toOrderDetailDTO)
            .collect(Collectors.toList());

        // Return OrderDto with the mapped data
        return new OrderDto(
            order.getId(),
            order.getAccount().getId(),
            order.getDiscount() != null ? order.getDiscount().getId() : 0,
            order.getDate(),
            order.getStatus(),
            order.getDescription(),
            order.getTotal_price(),
            order.getPayment_method(),
            order.getFee(),
            order.getFull_address(),
            orderDetailDtos // Ensure this is populated with OrderDetailDto
        );
    }
}
