package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.DTO.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Inventory;
import com.younes.reskilingproject.bookManagement.errorHandler.BookErrorResponse;
import com.younes.reskilingproject.bookManagement.errorHandler.BookNotFoundException;
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
    @GetMapping("books/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
    @PostMapping("/books")
    public Book addBook(@RequestBody BookRequestBody reqBody){
        Book newBook = bookService.saveBook(reqBody.getBook());
        Inventory newEntry = new Inventory(reqBody.getQuantity(), newBook);
        inventoryService.insertEntry(newEntry);
        return newBook;
    }
    @PutMapping("/books/{id}")
    public Book editBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }

    // for Inventory routes
    @GetMapping("/books/{id}/inventory")
    public Inventory getBookInventory(@PathVariable long id) {
        return inventoryService.getEntry(id);
    }
    @PostMapping("/books/{id}/inventory")
    public Inventory editBookInventory(@PathVariable long id, @RequestBody int quantity) {
        return inventoryService.updateEntry(id, quantity);
    }

    // add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exc) {
        BookErrorResponse customErr = new BookErrorResponse();

        customErr.setStatus(HttpStatus.NOT_FOUND.value());
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
}
