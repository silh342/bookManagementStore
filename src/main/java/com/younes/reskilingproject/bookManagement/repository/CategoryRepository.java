package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findCategoryByCategoryNameIgnoreCase(String categoryName);
    public Optional<List<Category>> findCategoryByCategoryNameContainingIgnoreCase(String categoryName);
}
