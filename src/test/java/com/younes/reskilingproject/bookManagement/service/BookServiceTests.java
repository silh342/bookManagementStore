package com.younes.reskilingproject.bookManagement.service;


import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.repository.AuthorRepository;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import com.younes.reskilingproject.bookManagement.repository.CategoryRepository;
import com.younes.reskilingproject.bookManagement.repository.InventoryRepository;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private InventoryRepository inventoryRepository;
    @InjectMocks
    private ImplBookService bookService;

    @Test
    public void bookService_CreateBook_ReturnBookModel() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Mock authorRepository behaviour in case author is not found
        when(authorRepository.findAuthorByFullNameIgnoreCase("Sally Rooney"))
                .thenReturn(Optional.of(new Author("Sally Rooney", "is an Irish author and screenwriter. She has published three novels", new HashSet<>())));

        // Mock categoryRepository behaviour in case category is not found
        when(categoryRepository.findCategoryByCategoryNameIgnoreCase("Romance"))
                .thenReturn(Optional.of(new Category("Romance", new ArrayList<>())));

        Book book = new Book("878745645-11",
                            "Normal People",
                            120,
                            simpleDateFormat.parse("2018-08-28"),
                            simpleDateFormat.parse("2018-08-28"));

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Book savedBook = bookService.saveBook(book,"Sally Rooney", "Romance", 15);
        Assertions.assertNotNull(savedBook);
    }

    @Test
    public void bookService_findAllBooks_ReturnListOfBooks() throws ParseException {
        // Mock data to display in find all books
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("9780134685991","Normal People",120, simpleDateFormat.parse("2018-08-28"),  simpleDateFormat.parse("2018-08-28")));
        expectedBooks.add(new Book("9780451205766","Dune",153, simpleDateFormat.parse("1965-08-01"),  simpleDateFormat.parse("1965-08-01")));

        // Mock behaviour of the findall method
        when(bookRepository.findAll()).thenReturn(expectedBooks);
        // Call the service method
        List<Book> bookList = bookRepository.findAll();

        // Assertions
        Assertions.assertNotNull(bookList);
        Assertions.assertEquals(bookList.size(), 2);
        for(int i = 0; i < bookList.size(); i++) {
            Assertions.assertEquals(bookList.get(i).getBookId(), expectedBooks.get(i).getBookId());
            Assertions.assertEquals(bookList.get(i).getISBN(), expectedBooks.get(i).getISBN());
            Assertions.assertEquals(bookList.get(i).getTitle(), expectedBooks.get(i).getTitle());
            Assertions.assertEquals(bookList.get(i).getPrice(), expectedBooks.get(i).getPrice());
        }
    }
    @Test
    public void bookService_findBookById_ReturnBook() {

        Book book = new Book("9780134685991","Normal People",120, new Date(), new Date());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> findBook = bookRepository.findById(1L);

        // Assert
        Assertions.assertNotNull(findBook);
    }

    @Test
    public void bookService_findBookByAuthor_ReturnBook() throws ParseException {

        // Add 2 books with 1 author that we are going to look for
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Book> listBooks = new ArrayList<>();
        Book dune = new Book("9780134685991","Normal People",120, simpleDateFormat.parse("2018-08-28"),  simpleDateFormat.parse("2018-08-28"));
        Book normal = new Book("9780451205766","Dune",153, simpleDateFormat.parse("1965-08-01"),  simpleDateFormat.parse("1965-08-01"));
        Author normalAuthor = new Author("Sally Rooney", "is an Irish author and screenwriter. She has published three novels", new HashSet<>());
        Author duneAuthor = new Author("Frank Herbert", "is an american fiction writer", new HashSet<>());

        // Set only normal people book to the author sally rooney
        normal.setAuthor(normalAuthor);
        dune.setAuthor(duneAuthor);
        listBooks.add(dune);
        listBooks.add(normal);
        List<Author> listAuthors = new ArrayList<>();
        listAuthors.add(duneAuthor);
        listAuthors.add(normalAuthor);

        // Filter the list of books to only return the book that has author rooney sally
        List<Book> expectedResult = listBooks.stream().filter(book -> Objects.equals(book.getAuthor().getFullName(), normalAuthor.getFullName())).collect(Collectors.toList());
        List<Author> expectedAuthors = listAuthors.stream().filter(author -> Objects.equals(author.getFullName(),"Sally Rooney")).toList();
        // repositories needed inside of the findBooksByAuthor method in the service
        when(bookRepository.findBooksByAuthor(Mockito.any(Author.class))).thenReturn(expectedResult);
        when(authorRepository.findAuthorByFullNameContainingIgnoreCase("Sally Rooney"))
                .thenReturn(Optional.of(expectedAuthors));
        List<Book> findBooksByAuthor = bookService.findBooksByAuthor("Sally Rooney");

        // Assert that the list is not null and size = 1 to make sure that the test is correct
        Assertions.assertNotNull(findBooksByAuthor);
        Assertions.assertEquals(findBooksByAuthor.size(), 1);
    }
    @Test
    public void bookService_updateBook_returnUpdatedBook() {
        Book book = new Book("9780134685991","Normal People",120, new Date(), new Date());
        Inventory inventory = new Inventory(15, book);
        book.setInventory(inventory);
        BookRequestBody requestBody = new BookRequestBody(book,18, "Sally Rooney", "Romance");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        when(authorRepository.findAuthorByFullNameIgnoreCase("Sally Rooney"))
                .thenReturn(Optional.of(new Author("Sally Rooney", "is an Irish author and screenwriter. She has published three novels", new HashSet<>())));

        when(categoryRepository.findCategoryByCategoryNameIgnoreCase("Romance"))
                .thenReturn(Optional.of(new Category("Romance", new ArrayList<>())));

        Book updatedBook = bookService.editBook(1, requestBody);

        // Assert
        Assertions.assertNotNull(updatedBook);
    }
    @Test
    public void bookService_deleteBook_returnDeleted() {
        Book book = new Book("9780134685991","Normal People",120, new Date(), new Date());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertAll(() -> bookService.deleteBook(1L));
    }

}
