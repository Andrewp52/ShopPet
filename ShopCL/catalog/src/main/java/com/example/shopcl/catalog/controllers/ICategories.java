package com.example.shopcl.catalog.controllers;

import com.example.shopcl.dto.CategoryDto;

import java.util.Set;

public interface ICategories {
    Set<CategoryDto> getAllCategories();
}
