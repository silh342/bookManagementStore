package com.younes.reskilingproject.bookManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.younes.reskillingproject.userManagement.security.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    @Column(name = "body")
    private String body;
    @Column(name = "rating")
    private byte rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"reviews","favoriteBooks"})
    private User reviewAuthor;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("likedByUsers")
    private Book reviewedBook;

    public Review() {};
    public Review(String body, byte rating, User reviewAuthor, Book reviewedBook) {
        this.body = body;
        this.rating = rating;
        this.reviewAuthor = reviewAuthor;
        this.reviewedBook = reviewedBook;
    }

    public long getReviewId() {
        return reviewId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public User getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(User reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public Book getReviewedBook() {
        return reviewedBook;
    }

    public void setReviewedBook(Book reviewedBook) {
        this.reviewedBook = reviewedBook;
    }
}
