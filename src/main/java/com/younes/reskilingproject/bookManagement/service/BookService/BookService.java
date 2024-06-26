package com.younes.reskilingproject.bookManagement.service.BookService;
import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAllBooks();
    public Book findBookById(long id);
    public Book saveBook(BookRequestBody requestBody);
    public Book editBook(long id, BookRequestBody book);
    public Book addBookToFavorites(long bookId, String username, boolean addOrRemove);
    public List<Book> getFavoriteBooksByUser(String username);
    public List<Book> findBooksByCategoryName(String categoryName);
    public List<Book> findBooksByAuthor(String authorName);
    public List<Book> findBooksByAllFields(String keyword);
    public List<Book> findAllBooksOrderByTitleAsc();
    public List<Book> findAllBooksOrderByTitleDesc();
    public void deleteBook(long id);
}
