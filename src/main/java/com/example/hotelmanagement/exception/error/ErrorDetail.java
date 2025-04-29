package com.example.hotelmanagement.exception.error;

public class ErrorDetail {
    private final String field, message;
    private final Object rejectedValue;

    public ErrorDetail(String field, String message, Object rejectedValue){
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}
