package com.chisoftware.contact.service;

import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactRespone;
import com.chisoftware.contact.model.entity.Contact;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContacts(Authentication authentication);

    ContactRespone addContact(Authentication authentication, ContactDTO request);

    ContactRespone editContact(Authentication authentication, Long contactId, ContactDTO request);

    ContactRespone deleteContact(Authentication authentication, Long contactId);
}
