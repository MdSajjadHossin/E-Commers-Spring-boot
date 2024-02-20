package com.springboot.entity;

import com.springboot.dto.ProductDto;
import com.springboot.dto.WishListDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public WishListDto getWishListDto(){
        WishListDto wishListDto = new WishListDto();

        wishListDto.setId(id);
        wishListDto.setPrice(product.getPrice());
        wishListDto.setProductDescription(product.getDescription());
        wishListDto.setProductName(product.getName());
        wishListDto.setReturnedImg(product.getImg());
        wishListDto.setUserId(user.getId());

        return wishListDto;
    }

}
