package com.springboot.repository;

import com.springboot.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepo extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUserId(Long userId);
}
