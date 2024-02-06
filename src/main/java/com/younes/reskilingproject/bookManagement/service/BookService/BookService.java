package com.younes.reskilingproject.bookManagement.service.BookService;
import com.younes.reskilingproject.bookManagement.DTO.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAllBooks();
    public Book findBookById(long id);
    public Book saveBook(Book book, String authorName, String categoryName, int quantity);
    public Book editBook(long id, BookRequestBody book);
    public List<Book> findBooksByCategoryName(String categoryName);
    public List<Book> findBooksByAuthor(String authorName);
    public List<Book> findAllBooksOrderByTitleAsc();
    public List<Book> findAllBooksOrderByTitleDesc();
    public void deleteBook(long id);
}
