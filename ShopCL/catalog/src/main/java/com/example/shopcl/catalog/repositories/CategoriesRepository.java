package com.example.shopcl.catalog.repositories;

import com.example.shopcl.catalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Set<Category>>findByParentId(Long parentId);
}
