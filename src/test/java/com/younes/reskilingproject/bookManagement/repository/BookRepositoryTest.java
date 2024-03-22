package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.errorHandler.BookException;
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
    @Autowired
    private AuthorRepository authorRepository;

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
        // Create book instance
        Book dune = new Book("7897856-12", "DUNE", 7, null, null, new Date(), new Date(), null);

        // Perform queries in H2 Db
        bookRepository.save(dune);
        Optional<Book> bookReturned = bookRepository.findById(dune.getBookId());

        // Assert
        Assertions.assertNotNull(bookReturned);
    }

    @Test
    public void test_bookRepository_findBooksByCategoryCustomQueryMethod() {

        // Create a book instance with a valid category
        Category category = new Category("Science fiction", new ArrayList<>());
        Book dune = new Book("7897856-12", "DUNE", 7, category, null, new Date(), new Date(), null);
        category.getBooks().add(dune);

        // Save it
        categoryRepository.save(category);
        bookRepository.save(dune);
        List<Book> booksByCategory = bookRepository.findBooksByCategory(category);

        // Assert
        Assertions.assertNotEquals(booksByCategory.size(), 0);
    }

    @Test
    public void test_bookRepository_findBooksByAuthorQueryMethod() {
        // Create a book with a valid author
        Author author = new Author(
                "FRANK HERBERT",
                "was an American science-fiction author best known for his 1965 novel Dune and its five sequels.",
                new HashSet<>());
        Book dune = new Book("7897856-12", "DUNE", 7, null, author, new Date(), new Date(), null);
        author.getBooks().add(dune);

        // Act
        authorRepository.save(author);
        bookRepository.save(dune);
        List<Book> booksByAuthor = bookRepository.findBooksByAuthor(author);

        // Assert
        Assertions.assertNotEquals(booksByAuthor.size(), 0);
    }

    @Test
    public void test_bookRepository_updateBook() {
        // ok this is interesting, it took me a while to analyse this, going to comeback to it
        Book aot = new Book("11111111-11", "attack on titan", 120, null, null, new Date(), new Date(), null);
        bookRepository.save(aot);
        Book savedBook = bookRepository.findById(aot.getBookId()).orElseThrow(() -> new BookException("Book Not found"));
        // this is new information to me but the aot and savedBook objects share the same reference in memory for some reason
        // When i tried to update the saved book attributes, automatically the aot object get updated ??? :(
        // So i had to clone the savedbook object before updating it so i can compare with it in assertion
        // because when i was assertingNotEquals updatedBook with savedBook tests are failed saying that Shingeki no kyojin are Equals
        // new Info Repository doesn't return a new instance of an object it just references the same object that you created the book with
        // hmmmmmmmmmmmmmmmmm
        Book clonedBook = savedBook.clone();
        savedBook.setTitle("Shingeki No Kyojin");
        savedBook.setPrice(150);
        savedBook.setISBN("2222222-22");
        Book updatedBook = bookRepository.save(savedBook);

        // Assert that new values are different from the original state of the book, however the ID should still be the same
        Assertions.assertNotNull(updatedBook);
        Assertions.assertEquals(updatedBook.getBookId(), clonedBook.getBookId());
        Assertions.assertNotEquals(updatedBook.getTitle(), clonedBook.getTitle());
        Assertions.assertNotEquals(updatedBook.getPrice(), clonedBook.getPrice());
        Assertions.assertNotEquals(updatedBook.getISBN(), clonedBook.getISBN());

        // Now the test passes
    }

    @Test
    public void test_bookRepository_deleteBook() {

        // save the book to DB
        Book aot = new Book("878745645-11", "attack on titan", 120, null, null, new Date(), new Date(), null);
        bookRepository.save(aot);

        // Delete the book that we saved, and list all books
        bookRepository.deleteById(aot.getBookId());
        List<Book> books = bookRepository.findAll();

        // Assert that the list is empty and deletion has been successful
        Assertions.assertEquals(books.size(), 0);
    }
}
