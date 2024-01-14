package com.younes.reskilingproject.bookManagement.DTO;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;

public class BookRequestBody {
    private Book book;
    private int quantity;

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
