package com.fpoly.java6.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fpoly.java6.entities.Cart_Detail;
import com.fpoly.java6.model.dto.ApiResponse;
import com.fpoly.java6.model.dto.CartItemDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.mapper.VariantMapper;
import com.fpoly.java6.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        List<CartItemDto> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 if the cart is empty
        }
        return ResponseEntity.ok(cartItems);  // Return 200 with the cart items
    }
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@RequestParam int accountId) {
        // Get the list of Cart_Detail by accountId
        List<Cart_Detail> cartItems = cartService.getCartItemsByAccountId(accountId);

        // Map Cart_Detail to CartItemDto and use VariantMapper for Variant conversion
        List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> {
            CartItemDto cartItemDto = new CartItemDto();
            
            // Map Cart_Detail to CartItemDto
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            
            // Map Variant to VariantDto using VariantMapper
            cartItemDto.setVariant(VariantMapper.tVariantDto(cartItem.getVariant()));

            // Map Product to ProductDto (assuming Cart_Detail has a reference to Product via Variant)
            ProductDto productDto = new ProductDto();
            productDto.setId(cartItem.getVariant().getProduct().getId()); // Assuming Cart_Detail -> Variant -> Product
            productDto.setName(cartItem.getVariant().getProduct().getName());
            productDto.setImage(cartItem.getVariant().getProduct().getImage());
         
            cartItemDto.setProduct(productDto); // Set ProductDto in CartItemDto

            return cartItemDto;
        }).collect(Collectors.toList());

        // Return the list of CartItemDto
        return ResponseEntity.ok(cartItemDtos);
    }   

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDto item) {
        try {
            cartService.addToCart(item);
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Return 400 with error message
        }
    }
    
    @PutMapping("/update/{itemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable int itemId, @RequestParam int quantity) {
        try {
            // Call service to update the cart item quantity
            boolean updated = cartService.updateCartItemQuantity(itemId, quantity);
            
            if (updated) {
                return ResponseEntity.ok("Số lượng sản phẩm đã được cập nhật.");
            } else {
                return ResponseEntity.status(400).body("Không thể cập nhật sản phẩm.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }


    @GetMapping("/totalPrice")
    public ResponseEntity<Integer> getTotalPrice(@RequestParam int accountId) {
        try {
            int totalPrice = cartService.getTotalPrice(accountId);
            return ResponseEntity.ok(totalPrice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable int itemId) {
        try {
            cartService.removeItemFromCart(itemId);
            return ResponseEntity.ok(new ApiResponse("success", "Sản phẩm đã được xóa khỏi giỏ hàng.", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ApiResponse("error", "Lỗi khi xóa sản phẩm: " + e.getMessage(), false));
        }
    }

    
    
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Giỏ hàng đã được xóa.");
    }
}
