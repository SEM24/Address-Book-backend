package com.chisoftware.user.handler.exception;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("You are not authorised");
    }
}
