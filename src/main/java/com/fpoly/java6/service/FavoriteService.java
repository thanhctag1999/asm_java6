package com.fpoly.java6.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Favorite;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.jpa.AccountJPA;
import com.fpoly.java6.jpa.FavoriteJPA;
import com.fpoly.java6.jpa.ProductJPA;
import com.fpoly.java6.model.dto.FavoriteDto;
import com.fpoly.java6.model.dto.ProductDto;
import com.fpoly.java6.model.dto.VariantDto;
import com.fpoly.java6.model.mapper.ProductMapper;
import com.fpoly.java6.model.mapper.VariantMapper;
@Service
public class FavoriteService {
    @Autowired
    private FavoriteJPA favoriteJPA;
    @Autowired
    private AccountJPA accountJPA;
    @Autowired
    private ProductJPA productJPA;

    public void addFavorite(int accountId, int productId) {
        Account account = accountJPA.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Product product = productJPA.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Favorite favoriteItem = new Favorite();
        favoriteItem.setAccount(account);
        favoriteItem.setProduct(product);
        favoriteJPA.save(favoriteItem);
    }
    

    
    public List<ProductDto> getFavoriteProducts(int userId) {
        List<Favorite> favorites = favoriteJPA.findByAccountId(userId);
        // Trả về danh sách các ProductDto thay vì VariantDto
        return favorites.stream()
                .filter(favorite -> favorite.getProduct() != null)  // Lọc những Favorite có Product
                .map(favorite -> ProductMapper.tProductDto(favorite.getProduct()))  // Chuyển đổi thành ProductDto
                .collect(Collectors.toList());
    }




    public void removeFavorite(int accountId, int productId) {
        favoriteJPA.deleteByAccountIdAndProductId(accountId, productId);
    }
}
