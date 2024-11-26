package com.fpoly.java6.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java6.entities.Product;
import com.fpoly.java6.model.dto.FavoriteDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.service.FavoriteService;

@RestController
@RequestMapping("/api")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	@GetMapping("/favorites/products")
	public ResponseEntity<List<ProductDto>> getFavoriteProducts(@RequestParam int accountId) {
	    List<ProductDto> productDtos = favoriteService.getFavoriteProducts(accountId);
	    if (productDtos.isEmpty()) {
	        return ResponseEntity.noContent().build();  // Trả về mã 204 nếu không có sản phẩm yêu thích
	    }
	    return ResponseEntity.ok(productDtos);  // Trả về mã 200 và danh sách sản phẩm yêu thích
	}


	
	@PostMapping("/favorites/add")
	public ResponseEntity<Map<String, String>> addToFavorites(@RequestParam int accountId, @RequestParam int productId) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        favoriteService.addFavorite(accountId, productId);
	        response.put("message", "Sản phẩm đã được thêm vào yêu thích.");
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@PostMapping("/favorites/remove")
	public ResponseEntity<Map<String, String>> removeFavorite(@RequestParam int accountId, @RequestParam int productId) {
	    try {
	        favoriteService.removeFavorite(accountId, productId);

	        // Trả về một thông báo thành công dưới dạng JSON
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Sản phẩm đã bị hủy yêu thích.");
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        // Trả về thông báo lỗi dưới dạng JSON
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Không thể hủy yêu thích sản phẩm: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}




}
