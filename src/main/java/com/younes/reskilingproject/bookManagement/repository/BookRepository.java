package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
