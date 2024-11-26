package com.fpoly.java6.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Address;
import com.fpoly.java6.model.dto.AccountDTO;
import com.fpoly.java6.model.dto.AddressDTO;

public class AccountMapper {
	 public static AccountDTO toAccountDto(Account account) {
	        List<AddressDTO> addressDtos = account.getAddresses().stream()
	                .map(AddressMapper::toDTO)
	                .collect(Collectors.toList());

	        return new AccountDTO(
	            account.getId(),
	            account.getName(),
	            account.getPhone(),
	            account.isGender(),
	            account.getEmail(),
	            account.getImage(),
	            account.getRole(),
	            account.isActived(),
	            addressDtos
	        );
	    }
}

