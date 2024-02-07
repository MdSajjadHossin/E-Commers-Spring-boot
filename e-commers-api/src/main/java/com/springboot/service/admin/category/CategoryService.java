package com.springboot.service.admin.category;

import com.springboot.dto.CategoryDto;
import com.springboot.entity.Category;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
