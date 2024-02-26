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
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImplBookService implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userService;

    @Autowired
    public ImplBookService(BookRepository bookRepo,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           InventoryRepository inventoryRepository,
                           UserRepository userService) {
        bookRepository = bookRepo;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.inventoryRepository = inventoryRepository;
        this.userService = userService;
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
    public void incrementViews(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookException("Could not find book"));
        book.setViews(book.getViews());
        bookRepository.save(book);
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
    public List<Book> getFavoriteBooksByUser(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist, please try another one"));
        return user.getFavoriteBooks().stream().toList();
    }
    @Override
    public List<Book> findAllBooksOrderByTitleAsc() {
        return bookRepository.findAllByOrderByTitleAsc();
    }
    @Override
    public List<Book> findAllBooksOrderByTitleDesc() {
        return bookRepository.findAllByOrderByTitleDesc();
    }

    public Book addBookToFavorites(long bookId, String username,boolean addOrRemove) {
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Book currentBook = bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not found"));
        if(addOrRemove) {
            currentBook.getLikedByUsers().remove(user);
            user.getFavoriteBooks().remove(currentBook);
        } else {
            currentBook.getLikedByUsers().add(user);
            user.getFavoriteBooks().add(currentBook);
        }
        userService.save(user);
        return bookRepository.save(currentBook);
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
