package com.younes.reskilingproject.bookManagement.errorHandler;

public class BookErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    // Constructor
    public BookErrorResponse() {}

    public BookErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // getter setter
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
