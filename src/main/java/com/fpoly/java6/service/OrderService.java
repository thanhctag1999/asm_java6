package com.fpoly.java6.service;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Cart_Detail;
import com.fpoly.java6.entities.Discount;
import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Order_Detail;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.AccountJPA;
import com.fpoly.java6.jpa.CartItemJPA;
import com.fpoly.java6.jpa.DiscountJPA;
import com.fpoly.java6.jpa.OrderJPA;
import com.fpoly.java6.jpa.Order_DetailJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.OrderRequestDto;
import com.fpoly.java6.model.dto.OrderResponseDto;
import com.fpoly.java6.model.dto.OrderDto;
import com.fpoly.java6.model.dto.AccountDTO;
import com.fpoly.java6.model.dto.OrderDetailDto;
import com.fpoly.java6.model.mapper.OrderMapper;
import com.fpoly.java6.model.mapper.AccountMapper;
import com.fpoly.java6.model.mapper.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderJPA orderRepository;
    
    @Autowired
    private CartItemJPA cartItemJPA;

    @Autowired
    private AccountJPA accountRepository;

    @Autowired
    private VariantJPA variantRepository;

    @Autowired
    private DiscountJPA discountRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    

    @Autowired
    private Order_DetailJPA order_DetailJPA;

//    public List<OrderDto> getAllOrders() {
//        List<Order> orders = orderRepository.findAll();
//        return orders.stream()
//                .map(OrderMapper::toOrderDTO)
//                .toList();
//    }
//    
    
    @Transactional
    public void createOrder(Order order, List<Order_Detail> orderDetails) {
        orderRepository.save(order);

        for (Order_Detail orderDetail : orderDetails) {
            orderDetail.setOrder(order);
            order_DetailJPA.save(orderDetail);
        }
    }

    private List<Order_Detail> createOrderDetails(List<OrderDetailDto> orderDetailDtos, Order order) {
        return orderDetailDtos.stream()
                .map(orderDetailDto -> {
                    Variant variant = variantRepository.findById(orderDetailDto.getVariantId())
                            .orElseThrow(() -> new IllegalArgumentException("Variant not found"));
                    return orderDetailMapper.toOrderDetailEntity(orderDetailDto, order, variant);
                }).toList();
    }
    
    
    public Order placeOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        Account account = accountRepository.findById(orderRequestDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        order.setAccount(account);
        order.setDescription(orderRequestDto.getDescription());
        order.setFull_address(orderRequestDto.getFullAddress());
        order.setPayment_method(orderRequestDto.getPaymentMethod());
        order.setStatus(orderRequestDto.getStatus());
        order.setDate(new Date());
        order.setFee(orderRequestDto.getFeeShip());
        order.setTotal_price(orderRequestDto.getTotalPrice());

        List<Order_Detail> orderDetails = new ArrayList<>();
        for (OrderDetailDto detailDto : orderRequestDto.getOrderDetails()) {
            Order_Detail orderDetail = toOrderDetailEntity(detailDto, order);
            orderDetails.add(orderDetail);
        }

        orderRepository.save(order);
        createOrder(order, orderDetails); 

        return order;
    }
    

    @Transactional
    public Order buyAllItemsInCart(int accountId, OrderRequestDto orderRequestDto) {
        // Lấy tài khoản của người dùng
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        // Lấy tất cả sản phẩm trong giỏ hàng của người dùng
        List<Cart_Detail> cartItems = cartItemJPA.findByAccountId(accountId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setAccount(account);
        order.setDescription(orderRequestDto.getDescription());
        order.setFull_address(orderRequestDto.getFullAddress());
        order.setPayment_method(orderRequestDto.getPaymentMethod());
        order.setStatus(orderRequestDto.getStatus());
        order.setDate(new Date());
        order.setFee(orderRequestDto.getFeeShip());
        
        // Tính tổng tiền của giỏ hàng
        int totalPrice = (int) cartItems.stream()
                .mapToDouble(item -> item.getVariant().getPrice() * item.getQuantity())
                .sum();
        order.setTotal_price(totalPrice);

        // Lưu đơn hàng
        orderRepository.save(order);

        // Tạo danh sách chi tiết đơn hàng từ giỏ hàng
        List<Order_Detail> orderDetails = cartItems.stream()
                .map(cartItem -> {
                    Order_Detail orderDetail = new Order_Detail();
                    orderDetail.setOrder(order);
                    orderDetail.setVariant(cartItem.getVariant());
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetail.setPrice(cartItem.getVariant().getPrice() * cartItem.getQuantity());
                    return orderDetail;
                }).collect(Collectors.toList());

        // Lưu chi tiết đơn hàng
        for (Order_Detail orderDetail : orderDetails) {
            order_DetailJPA.save(orderDetail);
        }

        // Xóa toàn bộ sản phẩm khỏi giỏ hàng sau khi mua
        cartItemJPA.deleteByAccountId(accountId);

        return order;
    }

    
    
    
	public Order_Detail toOrderDetailEntity(OrderDetailDto orderDetailDto, Order order) {
	    Variant variant = variantRepository.findById(orderDetailDto.getVariantId())
	            .orElseThrow(() -> new IllegalArgumentException("Variant not found"));
	    Order_Detail orderDetail = new Order_Detail();
	    
	    orderDetail.setVariant(variant);
	    orderDetail.setOrder(order); 
	    orderDetail.setQuantity(orderDetailDto.getQuantity()); 
	    orderDetail.setPrice(orderDetailDto.getPrice()); 
	    
	    return orderDetail;
	}

	public List<OrderDto> getOrdersByAccountId(int accountId) {
        List<Order> orders = orderRepository.findOrdersByAccountIdOrderByDateDesc(accountId);
        return orders.stream()	
                .map(OrderDetailDto::toOrderDto)
                .collect(Collectors.toList());
    }
	

    @Transactional
    public void deleteOrder(int orderId) {
      
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại!"));
   
        if (order.getStatus() != 1) {
            throw new RuntimeException("Không thể xóa đơn hàng, vì đơn hàng không đang xử lý!");
        }
        order_DetailJPA.deleteByOrderId(orderId);
        
    
        orderRepository.deleteById(orderId);
    }

	
	 public Order findById(int id) {
	        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
	    }

	  public List<Order> findByAccountIdAndStatus(int accountId, int status) {
	        return orderRepository.findByAccountIdAndStatus(accountId, status);
	    }


	    
	    public void save(Order order) {
	        orderRepository.save(order); 
	    }

}
