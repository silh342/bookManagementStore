package com.younes.reskilingproject.bookManagement.service.BookService;
import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAllBooks();
    public Book findBookById(long id);
    public Book saveBook(Book book);
    public void deleteBook(long id);
}
