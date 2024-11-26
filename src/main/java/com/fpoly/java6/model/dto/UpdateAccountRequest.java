package com.fpoly.java6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
	private String name;
	private String phone;
	private boolean gender;
	private String email;
	private String password; 
	private String image;
	private int role;
}
