package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private ImplBookService bookService;

    @Autowired
    public BookController(ImplBookService bookService) {
        this.bookService = bookService;
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
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }
    @PutMapping("/books/{id}")
    public Book editBook(@PathVariable long id ,@RequestBody Book book) {
        return bookService.editBook(id, book);
    }
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}
