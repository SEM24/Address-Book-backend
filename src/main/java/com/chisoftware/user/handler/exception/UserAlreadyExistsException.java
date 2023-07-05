package com.chisoftware.user.handler.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("This username is already taken");
    }
}
