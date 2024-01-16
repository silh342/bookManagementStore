package com.younes.reskilingproject.bookManagement.restController;


import com.younes.reskilingproject.bookManagement.entity.bookStore.Author;
import com.younes.reskilingproject.bookManagement.errorHandler.ErrorResponse;
import com.younes.reskilingproject.bookManagement.errorHandler.authorError.AuthorNotFoundException;
import com.younes.reskilingproject.bookManagement.service.AuthorService.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorController {

    private AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/authors")
    public List<Author> allAuthors() {
        return authorService.findAllAuthors();
    }
    @GetMapping("/authors/{id}")
    public Author findAuthor(@PathVariable long id) {
        return authorService.findAuthor(id);
    }
    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }
    @PutMapping("/authors")
    public Author editAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AuthorNotFoundException exc) {
        ErrorResponse customErr = new ErrorResponse();

        customErr.setStatus(HttpStatus.NOT_FOUND.value());
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
}
