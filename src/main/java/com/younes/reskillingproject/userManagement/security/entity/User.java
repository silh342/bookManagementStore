package com.younes.reskillingproject.userManagement.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Review;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> userRoles;
    @OneToMany(mappedBy = "reviewAuthor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reviewAuthor")
    private Set<Review> reviews = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "user_favorite_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonIgnoreProperties("reviews")
    private Set<Book> favoriteBooks = new HashSet<>();

    public User() {}
    public User(String username, String password, String email ,Set<Role> userRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRoles = userRoles;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }
    public Set<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
    public void setFavoriteBooks(Set<Book> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
