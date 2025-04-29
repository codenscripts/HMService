package com.example.hotelmanagement.exception.error;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error, message, path;
    private final List<ErrorDetail> fieldErrors;

    public ValidationErrorResponse(String message, List<ErrorDetail> fieldErrors, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = 400;
        this.error = "Bad request";
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getFieldErrors() {
        return fieldErrors;
    }

    public String getPath() {
        return path;
    }
}
