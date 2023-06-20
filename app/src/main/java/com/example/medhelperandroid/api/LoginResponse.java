package com.example.medhelperandroid.api;

public class LoginResponse {
    private String message;

    private String id;
    private String status;
    private String timestamp;
    private String error;
    private String trace;
    private String path;
    private String email;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getTrace() {
        return trace;
    }

    public String getPath() {
        return path;
    }

    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
}

