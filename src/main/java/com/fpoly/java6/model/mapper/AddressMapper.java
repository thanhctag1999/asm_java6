package com.fpoly.java6.model.mapper;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Address;
import com.fpoly.java6.model.dto.AddressDTO;
import com.fpoly.java6.service.AccountService;

public class AddressMapper {

    // Convert Address entity to AddressDTO
	public static AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDTO(
            address.getId(),
            address.getProvinceId(),
            address.getDistrictId(), 
            address.getWardCode(),
            address.getAddress(),
            address.getFull_address(),
            address.isActived(),
            address.getAccount().getId()
        );
    }

    // Convert AddressDTO to Address entity
	public static Address toEntity(AddressDTO addressDTO, Account account) {
	    if (addressDTO == null) {
	        return null;
	    }
	    Address address = new Address();
	    address.setId(addressDTO.getId());
	    address.setProvinceId(addressDTO.getProvinceId());
	    address.setDistrictId(addressDTO.getDistrictId());
	    address.setWardCode(addressDTO.getWardCode());
	    address.setAddress(addressDTO.getAddress());
	    address.setFull_address(addressDTO.getFullAddress());
	    address.setActived(addressDTO.isActived());
	    address.setAccount(account); // Set the Account object
	    return address;
	}



}