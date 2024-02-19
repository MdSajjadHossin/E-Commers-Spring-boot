package com.springboot.repository;

import com.springboot.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepo extends JpaRepository<FAQ, Long> {
}
