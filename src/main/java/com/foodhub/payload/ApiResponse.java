package com.foodhub.payload;

public class ApiResponse {
    private String message;

    public ApiResponse() {
        // Default constructor required for JSON deserialization
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
