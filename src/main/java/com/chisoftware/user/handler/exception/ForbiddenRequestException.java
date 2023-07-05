package com.chisoftware.user.handler.exception;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException(String text) {
        super(text);
    }

    public ForbiddenRequestException() {
        super("You don't have sufficient rights to perform this action");
    }
}
