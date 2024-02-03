package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.DTO.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.errorHandler.ErrorNotFoundException;
import com.younes.reskilingproject.bookManagement.errorHandler.bookError.BookNotFoundException;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import com.younes.reskilingproject.bookManagement.service.InventoryService.ImplInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private ImplBookService bookService;
    private ImplInventoryService inventoryService;

    @Autowired
    public BookController(ImplBookService bookService, ImplInventoryService inventoryService) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
    }
    @GetMapping("/books/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @GetMapping("/books/category/{name}")
    public List<Book> findBooksByCategory(@PathVariable String name) {
        return bookService.findBooksByCategoryName(name);
    }
    @GetMapping("/books/author/{name}")
    public List<Book> findBooksByAuthor(@PathVariable String name) {
        return bookService.findBooksByAuthor(name);
    }
    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
    @GetMapping("/books/sortedbytitle")
    public List<Book> getBooksByOrderAsc() {
        return bookService.findAllBooksOrderByTitleAsc();
    }
    @GetMapping("/books/sortedbytitledesc")
    public List<Book> getBooksByOrderDesc() {
        return bookService.findAllBooksOrderByTitleDesc();
    }
    @PostMapping(value = "/books")
    public Book addBook(@RequestBody BookRequestBody reqBody){
        // Add author and category if they don't exist
        return bookService.createBook(reqBody.getBook(),
                    reqBody.getAuthorName(), reqBody.getCategoryName(), reqBody.getQuantity());
    }
    @PutMapping("/books")
    public Book editBook(@RequestBody BookRequestBody requestBody) {
        return bookService.createBook(requestBody.getBook(), requestBody.getAuthorName(),
                requestBody.getCategoryName(), requestBody.getQuantity());
    }
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
    // for Inventory routes
    @GetMapping("/books/inventory/{id}")
    public Inventory getBookInventory(@PathVariable long id) {
        return inventoryService.getEntry(id);
    }
    @PostMapping("/books/inventory/{id}")
    public Inventory editBookInventory(@PathVariable long id, @RequestBody int quantity) {
        return inventoryService.updateEntry(id, quantity);
    }
    @DeleteMapping("/inventory/{id}")
    public void deleteInventory(@PathVariable long id) {
        inventoryService.deleteEntry(id);
    }

    // add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<ErrorNotFoundException> handleException(BookNotFoundException exc) {
        ErrorNotFoundException customErr = new ErrorNotFoundException();

        customErr.setStatus(HttpStatus.NOT_FOUND.value());
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
}
