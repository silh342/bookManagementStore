package com.younes.reskilingproject.bookManagement.dto;

public class FavoriteBook {
    private long bookId;
    private String username;
    private boolean addOrRemove;

    public long getBookId() {
        return bookId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isAddOrRemove() {
        return addOrRemove;
    }

}
