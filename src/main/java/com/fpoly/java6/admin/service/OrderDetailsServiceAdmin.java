package com.fpoly.java6.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpoly.java6.admin.repository.OrderDetailsRepository;
import com.fpoly.java6.entities.Order_Detail;

import java.util.List;

@Service
public class OrderDetailsServiceAdmin {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<Order_Detail> findByOrderId(int id) {
        return orderDetailsRepository.findByOrder_Id(id);
    }
    public List<Order_Detail> getOrderDetailsByOrderId(int id) {
        return orderDetailsRepository.findByOrder_Id(id);
    }
   
}
