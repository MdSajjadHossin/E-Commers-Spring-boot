package com.springboot.dto;

import lombok.Data;

@Data
public class AddProductCartDto {

    private Long userId;

    private Long productId;
}
