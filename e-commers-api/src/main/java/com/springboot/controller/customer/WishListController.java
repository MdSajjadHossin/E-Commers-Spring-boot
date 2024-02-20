package com.springboot.controller.customer;

import com.springboot.dto.WishListDto;
import com.springboot.entity.WishList;
import com.springboot.service.wishList.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/wishList")
    public ResponseEntity<?> addProductToWishList(@RequestBody WishListDto wishListDto){
        WishListDto postWishListDto = wishListService.addProductToWishList(wishListDto);
        if(postWishListDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postWishListDto);
    }

    @GetMapping("/wishList/{userId}")
    public ResponseEntity<List<WishListDto>> getWishListByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(wishListService.getWishListById(userId));
    }
}
