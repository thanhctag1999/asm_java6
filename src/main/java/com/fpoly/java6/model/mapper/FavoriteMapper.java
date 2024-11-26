package com.fpoly.java6.model.mapper;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Favorite;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.model.dto.FavoriteDto;

public class FavoriteMapper {

	public static FavoriteDto toFavoriteDto(Favorite favorite) {
		return new FavoriteDto(favorite.getId(), favorite.getAccount(), favorite.getProduct());
	}

	public static Favorite toFavorite(FavoriteDto favoriteDto) {
		Favorite favorite = new Favorite();
		favorite.setId(favoriteDto.getId());
		favorite.setAccount(new Account());
		favorite.setProduct(new Product());
		return favorite;
	}
}
