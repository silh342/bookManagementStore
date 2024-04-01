package com.younes.reskilingproject.bookManagement.repository;

import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;
import com.younes.reskillingproject.userManagement.security.entity.User;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;


    @Test
    public void should_ReturnReview_WhenSaved() {
        User author = new User("Younes SEBBAR", "28031996","younes@gmail.com", null);
        Book book = new Book("987545611","Jou7a",10, null, null);
        Review review = new Review("this is the worst book i have ever read in my entire life", (byte) 0, author, book);

        Review savedReview = reviewRepository.save(review);

        Assertions.assertNotNull(savedReview);
        Assertions.assertNotEquals(savedReview.getReviewId(), 0);
    }
    @Test
    public void should_ReturnListReviews() {
        User younes = userRepository.save(new User("Younes SEBBAR", "28031996","younes@gmail.com", null));
        User ayoub =  userRepository.save(new User("Ayoub MKIRA", "02081995","ayoub@gmail.com", null));
        Book book = bookRepository.save(new Book("987545611","Jou7a",10, null, null));
        reviewRepository.save(new Review("this is the worst book i have ever read in my entire life", (byte) 0, younes, book));
        reviewRepository.save(new Review("this is the Best book i have ever read in my entire life", (byte) 0, ayoub, book));

        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertNotNull(reviews);
        Assertions.assertEquals(reviews.size(), 2);
    }
    @Test
    public void should_DeleteReview() {
        User author = new User("Younes SEBBAR", "28031996","younes@gmail.com", null);
        Book book = new Book("987545611","Jou7a",10, null, null);
        Review review = new Review("this is the worst book i have ever read in my entire life", (byte) 0, author, book);
        Review savedReview = reviewRepository.save(review);

        reviewRepository.deleteById(savedReview.getReviewId());
        List<Review> listReviews = reviewRepository.findAll();

        Assertions.assertEquals(listReviews.size(), 0);



    }
}
