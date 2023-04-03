package com.example.shopcl.catalog.dtofactories;

import com.example.shopcl.catalog.entities.Category;
import com.example.shopcl.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoFactory implements DtoFactory<CategoryDto, Category> {

    @Override
    public CategoryDto getDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getParent() != null? category.getParent().getId() : null,
                category.getName(),
                category.getDescription()
                );
    }
}
