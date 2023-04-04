package com.example.shopcl.catalog.controllers;

import com.example.shopcl.catalog.exceptions.CategoryNotFoundException;
import com.example.shopcl.catalog.services.CategoryService;
import com.example.shopcl.dto.CategoryDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")

public class CategoriesController implements ICategories{
    private final CategoryService service;
    @GetMapping
    public Set<CategoryDto> getAllCategories(){
        return service.getAllCategories();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public void handleCatNotFoundException(CategoryNotFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
}
