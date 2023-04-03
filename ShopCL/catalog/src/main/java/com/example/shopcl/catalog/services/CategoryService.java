package com.example.shopcl.catalog.services;

import com.example.shopcl.catalog.dtofactories.DtoFactory;
import com.example.shopcl.catalog.entities.Category;
import com.example.shopcl.catalog.exceptions.CategoryNotFoundException;
import com.example.shopcl.catalog.repositories.CategoriesRepository;
import com.example.shopcl.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository repository;
    private final DtoFactory<CategoryDto, Category> dtoFactory;

    public Set<CategoryDto> getRootCategories(){
        return getChildCategoriesByParentId(null);
    }
    public Set<CategoryDto> getChildCategoriesByParentId(Long parentId){
        Set<Category> found = repository.findByParentId(parentId).orElseThrow(CategoryNotFoundException::new);
        return found.stream().map(dtoFactory::getDto).collect(Collectors.toSet());
    }

    public CategoryDto getCategoryById(Long id){
        Category found = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        return dtoFactory.getDto(found);
    }

}
