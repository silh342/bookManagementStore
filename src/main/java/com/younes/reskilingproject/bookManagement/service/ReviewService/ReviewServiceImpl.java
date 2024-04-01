package com.younes.reskilingproject.bookManagement.service.ReviewService;

import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import com.younes.reskilingproject.bookManagement.repository.ReviewRepository;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository,
                             BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Review addReview(String body, byte rating, String username, long bookId) {
        User user = userRepository.findByUsername(username).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
        Review review = new Review(body, rating, user, book);
        return reviewRepository.save(review);
    }
    @Override
    public Review editReview(Long reviewId, String updatedBody, byte updatedRating) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null) {
            review.setBody(updatedBody);
            review.setRating(updatedRating);
            return reviewRepository.save(review);
        }
        return null;
    }
    @Override
    public List<Review> findAllReviewsByBook(Book book){
        return this.reviewRepository.findReviewsByReviewedBook(book);
    }

    @Override
    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }
}
