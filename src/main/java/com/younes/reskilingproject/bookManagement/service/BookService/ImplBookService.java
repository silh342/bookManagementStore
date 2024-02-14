package com.younes.reskilingproject.bookManagement.service.BookService;

import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.errorHandler.AuthorException;
import com.younes.reskilingproject.bookManagement.errorHandler.BookException;
import com.younes.reskilingproject.bookManagement.errorHandler.CategoryException;
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
                    () -> new BookException("Could not find the book by the id " + id));
    }
    @Override
    public List<Book> findBooksByCategoryName(String categoryName) {
        List<Book> books = new ArrayList<>();
        List<Category> categories = categoryRepository.findCategoryByCategoryNameContainingIgnoreCase(categoryName)
                .orElseThrow(() -> new CategoryException("Category Not Found"));
        for(Category cat: categories) {
            books.addAll(bookRepository.findBooksByCategory(cat));
        }
        return books;
    }
    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();
        List<Author> authors = authorRepository.findAuthorByFullNameContainingIgnoreCase(authorName)
                .orElseThrow(() -> new AuthorException("Author not found"));
        for(Author aut: authors) {
            books.addAll(bookRepository.findBooksByAuthor(aut));
        }
        return books;
    }

    @Override
    public List<Book> findBooksByAllFields(String keyword) {
        return bookRepository.searchBooksByAllFields(keyword);
    }
    @Override
    public List<Book> findAllBooksOrderByTitleAsc() {
        return bookRepository.findAllByOrderByTitleAsc();
    }
    @Override
    public List<Book> findAllBooksOrderByTitleDesc() {
        return bookRepository.findAllByOrderByTitleDesc();
    }
    @Override
    public Book saveBook(Book newBook, String authorName, String categoryName, int quantity) {

        Author author = authorRepository.findAuthorByFullNameIgnoreCase(authorName)
                .orElseGet(() -> {
                    Set<Book> listAuthorBooks = new HashSet<>();
                    listAuthorBooks.add(newBook);
                    Author newAuthor = new Author(authorName, " ", listAuthorBooks);
                    return authorRepository.save(newAuthor);
                });
        Category category = categoryRepository.findCategoryByCategoryNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    List<Book> listBooksCategory = new ArrayList<>();
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
        newBook.setInventory(newEntry);

        return bookRepository.save(newBook);
    }
    @Override
    public Book editBook(long id, BookRequestBody book) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookException("Book Not Found"));
        Category existingCategory = categoryRepository.findCategoryByCategoryNameIgnoreCase(book.getCategoryName())
                .orElseGet(() -> {
                    List<Book> categoryBooks = new ArrayList<>();
                    categoryBooks.add(existingBook);
                    Category newCategory = new Category(book.getCategoryName(), categoryBooks);
                    return categoryRepository.save(newCategory);
                });
        Author existingAuthor = authorRepository.findAuthorByFullNameIgnoreCase(book.getAuthorName())
                .orElseGet(() -> {
                    Set<Book> authorBooks = new HashSet<>();
                    authorBooks.add(existingBook);
                    Author author = new Author(book.getAuthorName(),"", authorBooks);
                    return authorRepository.save(author);
                });

            existingBook.setTitle(book.getBook().getTitle());
            existingBook.setPrice(book.getBook().getPrice());
            existingBook.setISBN(book.getBook().getISBN());
            existingBook.setDateCreation(book.getBook().getDateCreation());
            existingBook.setDatePublication(book.getBook().getDatePublication());
            existingBook.setAuthor(existingAuthor);
            existingAuthor.getBooks().add(existingBook);
            existingBook.setCategory(existingCategory);
            existingCategory.getBooks().add(existingBook);

        if(existingBook.getInventory().getQuantity() != book.getQuantity()) {
            existingBook.getInventory().setQuantity(book.getQuantity());
        }
        return bookRepository.save(existingBook);
    }
    @Override
    public void deleteBook(long id) {
        if(findBookById(id) != null) bookRepository.deleteById(id);

    }
}
