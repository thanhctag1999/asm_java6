package com.fpoly.java6.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.model.dto.OrderDetailDto;
import com.fpoly.java6.model.mapper.OrderDetailMapper;
import com.fpoly.java6.service.OrderDetailService;

@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailController {
//	  @Autowired
//	    private OrderDetailService orderDetailService;
//
//	    @Autowired
//	    private OrderDetailMapper orderDetailMapper;
//	    
//	    @GetMapping
//	    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetails() {
//	        List<OrderDetailDto> orderDetailDtos = orderDetailService.getAllOrderDetails().stream()
//	                .map(OrderDetailMapper::toOrderDetailDTO)
//	                .collect(Collectors.toList());
//
//	        return new ResponseEntity<>(orderDetailDtos, HttpStatus.OK);
//	    }
//	    
//	    @GetMapping("/{id}")
//	    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable int id) {
//	        return orderDetailService.getOrderDetailById(id)
//	                .map(orderDetail -> new ResponseEntity<>(orderDetailMapper.toOrderDetailDTO(orderDetail), HttpStatus.OK))
//	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//	    }
//	    
//	    @PostMapping
//	    public ResponseEntity<OrderDetailDto> createOrderDetail(@RequestBody OrderDetailDto dto) {
//	        OrderDetailDto createdOrderDetail = orderDetailService.createOrderDetail(dto);
//	        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
//	    }
//	    
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deleteOrderDetail(@PathVariable int id) {
//	        boolean deleted = orderDetailService.deleteOrderDetail(id);
//	        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
}
