package com.chisoftware.contact.handler.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String text) {
        super(text);
    }
}
