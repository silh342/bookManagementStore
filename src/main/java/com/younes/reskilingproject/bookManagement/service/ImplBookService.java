package com.younes.reskilingproject.bookManagement.service;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplBookService implements BookSeervice {

    private BookRepository bookRepository;
    public ImplBookService(BookRepository bookRepo) {
        bookRepository = bookRepo;
    }
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    @Override
    public Book findBookById(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        Book book = null;
        if(result.isPresent()) book = result.get();
        return book;
    }

    @Override
    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
