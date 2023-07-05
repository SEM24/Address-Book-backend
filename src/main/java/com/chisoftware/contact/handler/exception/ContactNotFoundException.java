package com.chisoftware.contact.handler.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException() {
        super("Contact with such credentials is not found");
    }
}
