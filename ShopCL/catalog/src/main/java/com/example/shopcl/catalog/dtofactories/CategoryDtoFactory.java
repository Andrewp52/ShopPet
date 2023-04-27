package com.example.shopcl.catalog.dtofactories;

import com.example.shopcl.catalog.entities.Category;
import com.example.shopcl.dto.CategoryDto;
import com.example.shopcl.dto.DtoFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryDtoFactory implements DtoFactory<CategoryDto, Category> {

    @Override
    public CategoryDto getDto(Category category){
        return new CategoryDto(
                category.getId(),
                getParentId(category),
                category.getName(),
                category.getDescription(),
                getChildren(category)
                );
    }

    private Long getParentId(Category category){
        return category.getParent() != null? category.getParent().getId() : null;
    }

    private Set<CategoryDto> getChildren(Category category){
        if(category == null){
            return null;
        }
        return category.getCategories().stream()
                .map(this::getDto)
                .collect(Collectors.toSet());
    }
}
