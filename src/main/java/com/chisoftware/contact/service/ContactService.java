package com.chisoftware.contact.service;

import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactResponse;
import com.chisoftware.user.model.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactService {
    List<ContactDTO> getAllContacts(Authentication authentication);

    ContactResponse addContact(Authentication authentication, ContactDTO request);

    ContactResponse editContact(Authentication authentication, Long contactId, ContactDTO request);

    ContactResponse deleteContact(Authentication authentication, Long contactId);

    User getCurrentUser(String username);
}
