package com.younes.reskilingproject.bookManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "ISNB")
    private String ISBN;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private float price;
    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonBackReference(value = "category_book")
    private Category category;
    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonBackReference(value = "author_book")
    private Author author;
    @Column(name = "date_publication")
    private Date datePublication;
    @Column(name = "date_creation")
    private Date dateCreation;
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "inventory_books")
    private Inventory inventory;

    // Constructor
    public Book() {}
    public Book(String ISBN, String title, float price, Category category, Author author, Date datePublication, Date dateCreation, Inventory inventory) {
        this.ISBN = ISBN;
        this.title = title;
        this.price = price;
        this.category = category;
        this.author = author;
        this.datePublication = datePublication;
        this.dateCreation = dateCreation;
        this.inventory = inventory;
    }

    // Getter Setter
    public long getBookId() {
        return bookId;
    }
    public String getISBN() {
        return ISBN;
    }
    public String getTitle() {
        return title;
    }
    public float getPrice() {
        return price;
    }
    public Category getCategory() {
        return category;
    }
    public Author getAuthor() {
        return author;
    }
    public Date getDatePublication() {
        return datePublication;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // ToStringMethod
    @Override
    public String toString() {
        return "Book {" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", author=" + author +
                ", datePublication=" + datePublication +
                ", dateCreation=" + dateCreation +
                ", inventory=" + inventory +
                '}';
    }
}
