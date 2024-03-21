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

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test_bookRespository_SaveBook() {
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
        Assertions.assertEquals(savedBook.getAuthor(), newBook.getAuthor());
        Assertions.assertEquals(savedBook.getCategory(), newBook.getCategory());
        Assertions.assertEquals(savedBook.getInventory(), newBook.getInventory());
        Assertions.assertEquals(savedBook.getISBN(), newBook.getISBN());
        Assertions.assertEquals(savedBook.getTitle(), newBook.getTitle());
        Assertions.assertEquals(savedBook.getPrice(), newBook.getPrice());
    }
    @Test
    public void test_bookRepository_findAllBooks() {
        // arrange
        Book dune = new Book("7897856-12", "DUNE", 7, null, null, new Date(), new Date(), null);
        Book cmbyn = new Book("9997888-12", "CALL ME BY YOUR NAME", 180, null, null, new Date(), new Date(), null);

        // act
        bookRepository.save(dune);
        bookRepository.save(cmbyn);
        List<Book> listBooks = bookRepository.findAll();

        // assert
        Assertions.assertNotNull(listBooks);
        Assertions.assertEquals(listBooks.size(), 2);
    }
    @Test
    public void test_bookRepository_findBookById() {
        Book dune = new Book("7897856-12", "DUNE", 7, null, null, new Date(), new Date(), null);
        bookRepository.save(dune);
        Optional<Book> bookReturned = bookRepository.findById(dune.getBookId());
        Assertions.assertNotNull(bookReturned);
    }

    @Test
    public void test_bookRepository_findBooksByCategoryCustomQueryMethod() {
        Category category = new Category("Science fiction", new ArrayList<>());
        Book dune = new Book("7897856-12", "DUNE", 7, category, null, new Date(), new Date(), null);
        category.getBooks().add(dune);

        categoryRepository.save(category);
        bookRepository.save(dune);
        List<Book> booksByCategory = bookRepository.findBooksByCategory(category);

        Assertions.assertNotEquals(booksByCategory.size(), 0);
    }
}
