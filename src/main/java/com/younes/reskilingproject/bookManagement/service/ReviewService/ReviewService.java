package com.younes.reskilingproject.bookManagement.service.ReviewService;

import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;

import java.util.List;

public interface ReviewService {

     Review addReview(String body, byte rating, String username, long bookId);
     Review editReview(Long reviewId, String updatedBody, byte updatedRating);
     List<Review> findAllReviewsByBook(Book book);
     void deleteReview(long id);
}
