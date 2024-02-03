package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findBooksByCategory(Category category);

    //    SELECT * FROM book WHERE author_id = author.getAuthorId()
    public List<Book> findBooksByAuthor(Author author);
    //    SELECT * FROM book ORDER BY title ASC
    public List<Book> findAllByOrderByTitleAsc();
    //    SELECT * FROM book ORDER BY title DESC
    public List<Book> findAllByOrderByTitleDesc();
}
