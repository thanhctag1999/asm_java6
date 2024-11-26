package com.fpoly.java6.json;

import java.io.File;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.java6.entities.Product;

public class JsonWriter {
	public void saveProductsToJson() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/index";
		List<Product> products = restTemplate.getForObject(url, List.class);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File("products.json"), products);
			System.out.println("Dữ liệu đã được ghi vào file products.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
