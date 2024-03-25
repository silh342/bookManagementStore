package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test_categoryRepository_saveCategory() {
        Category category = new Category("Science fiction", new ArrayList<>());
        Category newCategory = categoryRepository.save(category);

        Assertions.assertNotNull(newCategory);
        Assertions.assertNotEquals(newCategory.getCategoryId(), 0);
    }

    @Test
    public void test_categoryRepository_findAllcategories() {
        Category sf = new Category("Science fiction", new ArrayList<>());
        Category drama = new Category("Drama", new ArrayList<>());
        Category action = new Category("Action", new ArrayList<>());

        categoryRepository.save(sf);
        categoryRepository.save(drama);
        categoryRepository.save(action);

        List<Category> listCategories = categoryRepository.findAll();

        Assertions.assertNotNull(listCategories);
        Assertions.assertEquals(listCategories.size(), 3);
    }
}
