package com.younes.reskilingproject.bookManagement.entity.bookStore;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Book> books;

    // Constructor
    public Category() {}
    public Category(String categoryName, List<Book> books) {
        this.categoryName = categoryName;
        this.books = books;
    }

    // Getter Setter
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
