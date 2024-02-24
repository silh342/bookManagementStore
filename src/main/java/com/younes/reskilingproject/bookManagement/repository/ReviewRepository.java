package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByReviewedBook(Book book);
}
