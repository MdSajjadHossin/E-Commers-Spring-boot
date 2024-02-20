package com.springboot.service.wishList;

import com.springboot.dto.ProductDto;
import com.springboot.dto.WishListDto;
import com.springboot.entity.Product;
import com.springboot.entity.User;
import com.springboot.entity.WishList;
import com.springboot.repository.ProductRepo;
import com.springboot.repository.UserRepo;
import com.springboot.repository.WishListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final WishListRepo wishListRepo;

    public WishListDto addProductToWishList(WishListDto wishListDto){
        Optional<Product> optionalProduct = productRepo.findById(wishListDto.getProductId());
        Optional<User> optionalUser = userRepo.findById(wishListDto.getUserId());

        if(optionalProduct.isPresent() && optionalUser.isPresent()){
            WishList wishList = new WishList();
            wishList.setProduct(optionalProduct.get());
            wishList.setUser(optionalUser.get());

            return wishListRepo.save(wishList).getWishListDto();
        }
        return null;
    }

    public List<WishListDto> getWishListById(Long userId){
        return wishListRepo.findAllByUserId(userId).stream().map(WishList::getWishListDto).collect(Collectors.toList());

    }

}
