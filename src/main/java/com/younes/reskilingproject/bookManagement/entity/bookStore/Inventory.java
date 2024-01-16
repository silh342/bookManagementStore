package com.younes.reskilingproject.bookManagement.entity.bookStore;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int quantity;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // Constructor
    public Inventory() {};
    public Inventory(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }

    // getter setter
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
}
