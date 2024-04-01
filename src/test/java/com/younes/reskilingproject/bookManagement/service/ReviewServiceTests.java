package com.younes.reskilingproject.bookManagement.service;

import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;
import com.younes.reskilingproject.bookManagement.repository.BookRepository;
import com.younes.reskilingproject.bookManagement.repository.ReviewRepository;
import com.younes.reskilingproject.bookManagement.service.ReviewService.ReviewServiceImpl;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Book book;
    private Review review;
    private User user;

    @BeforeEach
    public void initData() {
        book =  new Book("878745645-11",
                "Normal People",
                120,
               null,
               null);
        user = new User("Younes","123", "younes@gmail.com", new HashSet<>());
        review = new Review("GOOD BOOK", (byte) 4,user, book);
    }
    @Test
    public void ReviewService_CreateReview_ReturnReviewCreated() {
        when(bookRepository.findById(book.getBookId())).thenReturn(Optional.of(book));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        Review savedReview = reviewService.addReview("GOOD BOOK", (byte) 4,user.getUsername(), book.getBookId());

        Assertions.assertNotNull(savedReview);
        Assertions.assertEquals(savedReview.getReviewAuthor(), user);
        Assertions.assertEquals(savedReview.getReviewedBook(), book);
    }

    @Test
    public void ReviewService_FindReviewsByBook_ReturnListOfReviews() {
        when(reviewRepository.findReviewsByReviewedBook(book)).thenReturn(Collections.singletonList(review));
        book.getReviews().add(review);
        List<Review> listReviews = reviewService.findAllReviewsByBook(book);

        Assertions.assertNotNull(listReviews);
        Assertions.assertEquals(listReviews.size(), 1);
    }

}
