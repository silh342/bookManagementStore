package com.younes.reskilingproject.bookManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String description;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "author_book")
    private Set<Book> books;

    // Constructor

    public Author() {

    }
    public Author(String fullName, String description, Set<Book> books) {
        this.fullName = fullName;
        this.description = description;
        this.books = books;
    }

    // Getter Setter
    public long getAuthorId() {
        return authorId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Set<Book> getBooks() {
        return books;
    }
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    // ToStringMethod

    @Override
    public String toString() {
        return "Author {" +
                "authorId=" + authorId +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
