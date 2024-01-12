package com.younes.reskilingproject.bookManagement.service.AuthorService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Author;

import java.util.List;

public interface AuthorService {
    public Author addAuthor(Author newAuthor);
    public List<Author> findAllAuthors();
    public Author findAuthor(long id);
    public void deleteAuthor(long id);

}
