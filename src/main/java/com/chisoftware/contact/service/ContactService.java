package com.chisoftware.contact.service;

import com.chisoftware.contact.model.dto.CreateContactDTO;
import com.chisoftware.contact.model.dto.ContactRespone;
import org.springframework.security.core.Authentication;

public interface ContactService {
    ContactRespone addContact(Authentication authentication, CreateContactDTO request);

    ContactRespone deleteContact(Authentication authentication, Long contactId);
}
