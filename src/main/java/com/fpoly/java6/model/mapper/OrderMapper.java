package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fpoly.java6.entities.Order;
import com.fpoly.java6.model.dto.AccountDTO;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.model.dto.OrderDetailDto;
import com.fpoly.java6.model.dto.OrderDto;
import com.fpoly.java6.model.dto.OrderRequestDto;
import com.fpoly.java6.model.dto.OrderResponseDto;

@Component
public class OrderMapper {
	 public static OrderDto toOrderDto(Order order) {
	        // Convert Account entity to AccountDTO
	        AccountDTO accountDto = AccountMapper.toAccountDto(order.getAccount());

	        // Convert Address entities to AddressDTOs
	        List<AddressDTO> addressDtos = order.getAccount().getAddresses().stream()
	                .map(AddressMapper::toDTO)
	                .collect(Collectors.toList());

	        // Return the OrderDTO
	        return new OrderDto();
	    }


    public static OrderResponseDto toOrderResponseDTO(Order order) {
        return new OrderResponseDto(order.getId(), true, "Đặt hàng thành công!");
    }
}
