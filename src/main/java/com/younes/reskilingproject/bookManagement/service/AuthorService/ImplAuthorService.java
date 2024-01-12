package com.younes.reskilingproject.bookManagement.service.AuthorService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Author;
import com.younes.reskilingproject.bookManagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplAuthorService implements AuthorService {

    private AuthorRepository authorRepository;
    @Autowired
    public ImplAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @Override
    public Author addAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }
    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author findAuthor(long id) {
        return authorRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }
}
