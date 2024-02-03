package com.younes.reskilingproject.bookManagement.DTO;

import com.younes.reskilingproject.bookManagement.entity.Book;

public class BookRequestBody {
    private Book book;
    private int quantity;
    private String authorName;
    private String categoryName;

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
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "BookRequestBody{" +
                "book=" + book.getTitle() +
                ", quantity=" + quantity +
                ", authorName='" + authorName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
