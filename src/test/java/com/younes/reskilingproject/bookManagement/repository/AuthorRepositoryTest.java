package com.younes.reskilingproject.bookManagement.repository;


import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.errorHandler.AuthorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void test_authorRepository_getAllAuthors() {

        Author hajime = new Author(
                "Hajime Isayama",
                "Known for being the author of the popular manga attack on titan",
                new HashSet<>());
        Author kishi = new Author(
                "Masashi Kishimoto",
                "Known for being the author of the popular manga naruto",
                new HashSet<>());

        authorRepository.save(hajime);
        authorRepository.save(kishi);
        List<Author> listAuthors = authorRepository.findAll();

        Assertions.assertNotNull(listAuthors);
        Assertions.assertEquals(listAuthors.size(), 2);
    }
    @Test
    public void test_authorRepository_saveAuthor() {
        Author hajime = new Author(
                "Hajime Isayama",
                "Known for being the author of the popular manga attack on titan",
                new HashSet<>());
        Author savedAuthor = authorRepository.save(hajime);

        Assertions.assertNotNull(savedAuthor);
        Assertions.assertNotEquals(savedAuthor.getAuthorId(), 0);
    }
    @Test
    public void test_authorRepository_findAuthorByid() {
        Author hajime = new Author(
                "Hajime Isayama",
                "Known for being the author of the popular manga attack on titan",
                new HashSet<>());
        authorRepository.save(hajime);
        Author findAuthor = authorRepository.findById(hajime.getAuthorId()).orElseThrow(() -> new AuthorException("Author not found"));

        Assertions.assertNotNull(findAuthor);
    }
    @Test
    public void test_authorRepository_deleteAuthor(){
        Author hajime = new Author(
                "Hajime Isayama",
                "Known for being the author of the popular manga attack on titan",
                new HashSet<>());
        authorRepository.save(hajime);
        authorRepository.deleteById(hajime.getAuthorId());

        Author author = authorRepository.findById(hajime.getAuthorId()).orElse(null);
        Assertions.assertNull(author);
    }
}
