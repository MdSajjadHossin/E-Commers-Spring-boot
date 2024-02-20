package com.springboot.service.wishList;

import com.springboot.dto.WishListDto;

import java.util.List;

public interface WishListService {

    WishListDto addProductToWishList(WishListDto wishListDto);
    List<WishListDto> getWishListById(Long userId);
}
