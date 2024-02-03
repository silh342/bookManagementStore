package com.younes.reskilingproject.bookManagement.service.AuthorService;

import com.younes.reskilingproject.bookManagement.entity.Author;

import java.util.List;

public interface AuthorService {
    public Author saveAuthor(Author newAuthor);
    public List<Author> findAllAuthors();
    public Author findAuthor(long id);
    public void deleteAuthor(long id);

}
