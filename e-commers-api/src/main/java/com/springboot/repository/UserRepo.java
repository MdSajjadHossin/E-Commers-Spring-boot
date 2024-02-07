package com.springboot.repository;

import com.springboot.entity.User;
import com.springboot.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String username);

    User findByRole(UserRole userRole);
}
