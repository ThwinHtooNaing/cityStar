package com.cityStar.exception;

public class EmailDuplicationException extends RuntimeException {
    public EmailDuplicationException(String message) {
        super(message);
    }
}
