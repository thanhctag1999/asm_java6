package com.fpoly.java6.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpoly.java6.admin.repository.OrderRepository;
import com.fpoly.java6.entities.Order;

import java.util.List;

@Service
public class OrderServiceAdmin {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll(); // Tìm tất cả các đơn hàng từ cơ sở dữ liệu
    }
    public Order findById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public void updateOrderStatus(int orderId, int status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
    }

}
