package com.younes.reskilingproject.bookManagement.restController;


import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.service.AuthorService.ImplAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AuthorController {
    private ImplAuthorService authorService;
    @Autowired
    public AuthorController(ImplAuthorService authorService) {
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

}
