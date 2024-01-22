package com.younes.reskilingproject.bookManagement.service.BookService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Author;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Category;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Inventory;
import com.younes.reskilingproject.bookManagement.errorHandler.bookError.BookNotFoundException;
import com.younes.reskilingproject.bookManagement.repository.AuthorRepository;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import com.younes.reskilingproject.bookManagement.repository.CategoryRepository;
import com.younes.reskilingproject.bookManagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImplBookService implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public ImplBookService(BookRepository bookRepo,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           InventoryRepository inventoryRepository) {
        bookRepository = bookRepo;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    @Override
    public Book findBookById(long id) {
        return bookRepository.findById(id).orElseThrow(
                    () -> new BookNotFoundException("Could not find the book by the id " + id));
    }
    @Override
    public Book createBook(Book newBook, String authorName, String categoryName, int quantity) {
        Set<Book> listAuthorBooks = new HashSet<>();
        List<Book> listBooksCategory = new ArrayList<>();

        Author author = authorRepository.findAuthorByFullName(authorName)
                .orElseGet(() -> {
                    listAuthorBooks.add(newBook);
                    Author newAuthor = new Author(authorName, " ", listAuthorBooks);
                    return authorRepository.save(newAuthor);
                });
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    listBooksCategory.add(newBook);
                    Category newCategory = new Category(categoryName, listBooksCategory);
                    return categoryRepository.save(newCategory);
                });

        author.getBooks().add(newBook);
        category.getBooks().add(newBook);
        newBook.setAuthor(author);
        newBook.setCategory(category);

        Inventory newEntry = new Inventory(quantity, newBook);
        inventoryRepository.save(newEntry);

        return bookRepository.save(newBook);
    }
    @Override
    public void deleteBook(long id) {
        if(findBookById(id) != null) bookRepository.deleteById(id);

    }
}
