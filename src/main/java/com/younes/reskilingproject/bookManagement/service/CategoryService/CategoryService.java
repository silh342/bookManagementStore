package com.younes.reskilingproject.bookManagement.service.CategoryService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAllCategories();
    public Category addCategory(Category newCategory);
    public Category findCategoryByName(String name);
    public void deleteCategory(long id);
}
