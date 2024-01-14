package com.younes.reskilingproject.bookManagement.service.BookService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import com.younes.reskilingproject.bookManagement.errorHandler.BookNotFoundException;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplBookService implements BookService {

    private BookRepository bookRepository;
    public ImplBookService(BookRepository bookRepo) {
        bookRepository = bookRepo;
    }
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    @Override
    public Book findBookById(long id) {
        return bookRepository.findById(id).orElseThrow(
                    () -> new BookNotFoundException("Could not find the book by the id " + id));
    }
    @Override
    public Book saveBook(Book newBook) {
        return bookRepository.save(newBook);
    }
    @Override
    public void deleteBook(long id) {
        if(findBookById(id) != null) bookRepository.deleteById(id);

    }
}
