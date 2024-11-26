package com.fpoly.java6.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	private int  id;
    private String provinceId;
    private String districtId;
    private String wardCode;
    private String address;
    private String fullAddress;
    private boolean isActived;
    private int accountId;
}