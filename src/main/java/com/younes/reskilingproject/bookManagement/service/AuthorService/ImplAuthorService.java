package com.younes.reskilingproject.bookManagement.service.AuthorService;

import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.errorHandler.AuthorException;
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
    public Author saveAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }
    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author findAuthor(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException("Author not found"));
    }
    @Override
    public void deleteAuthor(long id) {
        if(findAuthor(id) != null) authorRepository.deleteById(id);
    }
}
