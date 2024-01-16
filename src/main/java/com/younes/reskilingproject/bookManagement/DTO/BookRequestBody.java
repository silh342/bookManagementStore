package com.younes.reskilingproject.bookManagement.DTO;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Book;

public class BookRequestBody {
    private Book book;

    // TODO added category and author field to determine them when adding a book
    private String categoryName;
    private String authorName;
    private int quantity;

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String category) {
        this.categoryName = categoryName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String author) {
        this.authorName = authorName;
    }

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
