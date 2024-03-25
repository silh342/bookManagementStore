package com.younes.reskilingproject.bookManagement.service;


import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

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
}
