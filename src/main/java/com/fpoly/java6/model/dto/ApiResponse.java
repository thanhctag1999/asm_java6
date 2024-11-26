package com.fpoly.java6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	private String status;
	private String message;
	private boolean success;
	
	 public ApiResponse(String message, boolean success) {
	        this.status = success ? "success" : "error"; // Tự động xác định status
	        this.message = message;
	        this.success = success;
	    }
}
