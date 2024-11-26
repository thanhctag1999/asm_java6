package com.fpoly.java6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.OrderJPA;
import com.fpoly.java6.jpa.Order_DetailJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.OrderDetailDto;
import com.fpoly.java6.model.mapper.OrderDetailMapper;

@Service
public class OrderDetailService {
	@Autowired
    private OrderJPA orderJPA;

    @Autowired
    private VariantJPA variantJPA;

    @Autowired
    private Order_DetailJPA order_DetailJPA;

    public List<Order_Detail> getAllOrderDetails() {
        return order_DetailJPA.findAll();
    }
    
    public Optional<Order_Detail> getOrderDetailById(int id) {
        return order_DetailJPA.findById(id);
    }
    
    public OrderDetailDto createOrderDetail(OrderDetailDto dto) {
        // Tìm Order và Variant từ repository
        Order order = orderJPA.findById(dto.getOrderId())
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        Variant variant = variantJPA.findById(dto.getVariantId())
            .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        // Chuyển DTO thành entity và lưu
        Order_Detail orderDetail = OrderDetailMapper.toOrderDetailEntity(dto, order, variant);
        orderDetail = order_DetailJPA.save(orderDetail);

        return OrderDetailMapper.toOrderDetailDTO(orderDetail);
    }
    
    public boolean deleteOrderDetail(int id) {
        if (order_DetailJPA.existsById(id)) {
        	order_DetailJPA.deleteById(id);
            return true;
        }
        return false;
    }

}
