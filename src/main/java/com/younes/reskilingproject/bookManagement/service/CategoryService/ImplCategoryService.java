package com.younes.reskilingproject.bookManagement.service.CategoryService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Category;
import com.younes.reskilingproject.bookManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplCategoryService implements CategoryService{

    private CategoryRepository categoryRepository;
    @Autowired
    public ImplCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public Category addCategory(Category newCategory) {
        return categoryRepository.save(newCategory);
    }
    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }
    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
