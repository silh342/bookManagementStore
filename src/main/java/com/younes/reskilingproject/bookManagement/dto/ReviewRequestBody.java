package com.younes.reskilingproject.bookManagement.dto;

public class ReviewRequestBody {
    private String body;
    private byte rating;
    private String username;
    private long bookId;

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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public long getBookId() {
        return bookId;
    }
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
