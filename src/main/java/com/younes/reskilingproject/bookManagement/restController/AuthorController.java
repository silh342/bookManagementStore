package com.younes.reskilingproject.bookManagement.restController;


import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.service.AuthorService.ImplAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Author Endpoints")
public class AuthorController {
    private ImplAuthorService authorService;
    @Autowired
    public AuthorController(ImplAuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get list of all authors")
    @GetMapping("/authors")
    public List<Author> allAuthors() {
        return authorService.findAllAuthors();
    }
    @Operation(summary = "Get Author by id")
    @GetMapping("/authors/{id}")
    public Author findAuthor(@PathVariable long id) {
        return authorService.findAuthor(id);
    }
    @Operation(summary = "Add new author")
    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }
    @Operation(summary = "Update existing author")
    @PutMapping("/authors")
    public Author editAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }
    @Operation(summary = "Delete author by id")
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }
}
