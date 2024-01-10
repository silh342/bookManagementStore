package com.younes.reskilingproject.bookManagement.service;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;

import java.util.List;

public interface BookSeervice {
    public List<Book> findAllBooks();
    public Book findBookById(Long id);
    public Book addBook(Book newBook);
    public void deleteBook(Long id);
}
