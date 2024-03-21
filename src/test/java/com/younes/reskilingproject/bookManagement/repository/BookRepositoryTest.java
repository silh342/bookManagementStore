package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void bookRespository_SaveBook() {
        // Arrange
        Author author = new Author(
                "FRANK HERBERT",
                "Best known for being the author of the best selling sci-fi book Dune",
                new HashSet<>());
        Category category = new Category("Science fiction", new ArrayList<>());
        Inventory inventory = new Inventory(12, null);
        Book newBook = new Book("7897856-12", "DUNE", 7, category, author, new Date(), new Date(), inventory);
        author.getBooks().add(newBook);
        category.getBooks().add(newBook);
        inventory.setBook(newBook);

        // Act
        Book savedBook = bookRepository.save(newBook);

        // Assert
        Assertions.assertNotNull(savedBook);
        Assertions.assertNotEquals(savedBook.getBookId(), 0);
    }
}
