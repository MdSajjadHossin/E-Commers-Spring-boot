package com.springboot.repository;

import com.springboot.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);
}
