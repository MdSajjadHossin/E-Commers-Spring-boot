package com.springboot.entity;

import com.springboot.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
}
