package com.fpoly.java6.model.mapper;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.model.dto.OrderDetailDto;
@Component
public class OrderDetailMapper {
    public static Order_Detail toOrderDetailEntity(OrderDetailDto dto, Order order, Variant variant) {
        if (order == null || variant == null) {
            throw new IllegalArgumentException("Order and Variant cannot be null");
        }

        Order_Detail orderDetail = new Order_Detail();
        orderDetail.setId(dto.getId());
        orderDetail.setOrder(order);
        orderDetail.setVariant(variant);
        orderDetail.setQuantity(dto.getQuantity());
        orderDetail.setPrice(dto.getPrice());

        return orderDetail;
    }

    public static OrderDetailDto toOrderDetailDTO(Order_Detail orderDetail) {
        if (orderDetail == null) {
            throw new IllegalArgumentException("OrderDetail cannot be null");
        }

        OrderDetailDto dto = new OrderDetailDto();
        dto.setId(orderDetail.getId());
        dto.setOrderId(orderDetail.getOrder().getId());
        dto.setVariantId(orderDetail.getVariant().getId());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setPrice(orderDetail.getPrice());

        return dto;
    }
}
