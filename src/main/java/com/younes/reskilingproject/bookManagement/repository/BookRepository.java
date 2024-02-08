package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findBooksByCategory(Category category);

    //    SELECT * FROM book WHERE author_id = author.getAuthorId()
    public List<Book> findBooksByAuthor(Author author);
    //    SELECT * FROM book ORDER BY title ASC
    public List<Book> findAllByOrderByTitleAsc();
    //    SELECT * FROM book ORDER BY title DESC
    public List<Book> findAllByOrderByTitleDesc();

    @Query("SELECT b FROM Book b " +  "LEFT JOIN b.author a " + "LEFT JOIN b.category c " +
     "WHERE lower(b.title) LIKE lower(concat('%', :keyword, '%')) " + "OR lower(b.ISBN) LIKE lower(concat('%', :keyword, '%'))  " +
    "OR lower(a.fullName) LIKE lower(concat('%', :keyword, '%'))  " + "OR lower(c.categoryName) LIKE lower(concat('%', :keyword, '%')) ")
    List<Book> searchBooksByAllFields(String keyword);
}
