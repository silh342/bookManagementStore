package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private ImplBookService bookService;

    @Autowired
    public BookController(ImplBookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
    @GetMapping("getbook")
    public Book findBook(@RequestParam(value = "id") Long id) {
        return bookService.findBookById(id);
    }
}
