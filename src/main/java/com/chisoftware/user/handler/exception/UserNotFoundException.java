package com.chisoftware.user.handler.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User with such credentials is not found");
    }
}
