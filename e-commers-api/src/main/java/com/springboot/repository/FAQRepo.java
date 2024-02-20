package com.springboot.repository;

import com.springboot.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepo extends JpaRepository<FAQ, Long> {
    List<FAQ> findAllByProductId(Long productId);
}
