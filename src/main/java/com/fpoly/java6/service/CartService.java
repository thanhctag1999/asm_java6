package com.fpoly.java6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Cart_Detail;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Variant;
import com.fpoly.java6.jpa.AccountJPA;
import com.fpoly.java6.jpa.CartItemJPA;
import com.fpoly.java6.jpa.ProductJPA;
import com.fpoly.java6.jpa.VariantJPA;
import com.fpoly.java6.model.dto.CartItemDto;
@Service
public class CartService {

    @Autowired
    private CartItemJPA cartItemJPA;
    @Autowired
    private AccountJPA accountJPA;
    @Autowired
    private VariantJPA variantJPA;
    @Autowired
    private ProductJPA productJPA;
    
    private List<CartItemDto> cart = new ArrayList<>();

    public List<CartItemDto> getCartItems() {
        return cart;
    }

    public List<Cart_Detail> getCartItemsByAccountId(int accountId) {
        // Tìm Account theo accountId
        accountJPA.findById(accountId).orElseThrow(() -> 
            new IllegalArgumentException("Không tìm thấy tài khoản"));

        // Lấy tất cả Cart_Detail theo accountId
        return cartItemJPA.findByAccountId(accountId);
    }

    public void addToCart(CartItemDto item) {
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0.");
        }

        Account account = accountJPA.findById(item.getAccountId()).orElseThrow(() -> 
            new IllegalArgumentException("Không tìm thấy tài khoản."));
        
        Variant variant = variantJPA.findById(item.getVariantId()).orElseThrow(() -> 
            new IllegalArgumentException("Không tìm thấy sản phẩm với mã variant này."));

        Cart_Detail cartDetail = new Cart_Detail();
        cartDetail.setAccount(account);
        cartDetail.setVariant(variant);
        cartDetail.setQuantity(item.getQuantity());

        cartItemJPA.save(cartDetail);
    }

    public boolean updateCartItemQuantity(int itemId, int quantity) {
        // Fetch the cart item by itemId
        Cart_Detail cartItem = cartItemJPA.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng"));

        // Ensure the quantity is valid (greater than 0)
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ.");
        }

        // Update the quantity
        cartItem.setQuantity(quantity);
        
        // Save the updated cart item
        cartItemJPA.save(cartItem);
        
        return true;  // Return true to indicate the update was successful
    }


    // Tính tổng giá giỏ hàng cho accountId
    public void setTotalPrice(int accountId) {
        List<Cart_Detail> cartItems = cartItemJPA.findByAccountId(accountId);
        int totalPrice = 0;

        for (Cart_Detail item : cartItems) {
            int itemPrice = item.getVariant().getPrice();  // Lấy giá sản phẩm
            int itemQuantity = item.getQuantity();  // Lấy số lượng sản phẩm
            totalPrice += itemPrice * itemQuantity;  // Cộng dồn giá
        }

        // Cập nhật tổng giá vào cơ sở dữ liệu hoặc trả về
        // Ví dụ: bạn có thể lưu tổng giá vào bảng giỏ hàng hoặc trả về tổng giá cho giao diện
        System.out.println("Tổng tiền giỏ hàng: " + totalPrice);
    }

    // Trả về tổng giá cho giỏ hàng của accountId
    public int getTotalPrice(int accountId) {
        List<Cart_Detail> cartItems = cartItemJPA.findByAccountId(accountId);
        int totalPrice = 0;

        for (Cart_Detail item : cartItems) {
            int itemPrice = item.getVariant().getPrice();
            int itemQuantity = item.getQuantity();
            totalPrice += itemPrice * itemQuantity;
        }
        return totalPrice;
    }

    public void removeItemFromCart(int itemId) {
        // Tìm sản phẩm trong giỏ hàng
        Cart_Detail cartItem = cartItemJPA.findById(itemId).orElseThrow(() -> 
            new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng"));

        // Xóa sản phẩm
        cartItemJPA.delete(cartItem);
    }


    
    public void clearCart() {
        cart.clear();
    }
}
