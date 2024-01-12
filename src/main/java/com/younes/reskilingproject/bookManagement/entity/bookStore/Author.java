package com.younes.reskilingproject.bookManagement.entity.bookStore;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String description;
    // TODO add warning when trying to delete the author that his books are going to be deleted
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

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
        return "Author{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
