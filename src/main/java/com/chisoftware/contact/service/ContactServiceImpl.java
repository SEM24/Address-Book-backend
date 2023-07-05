package com.chisoftware.contact.service;

import com.chisoftware.contact.ContactRepository;
import com.chisoftware.contact.handler.exception.ContactNotFoundException;
import com.chisoftware.contact.model.dto.ContactRespone;
import com.chisoftware.contact.model.dto.CreateContactDTO;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.user.handler.exception.ForbiddenRequestException;
import com.chisoftware.user.model.entity.User;
import com.chisoftware.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepo;
    private UserService userService;

    @Override
    public ContactRespone addContact(Authentication authentication, CreateContactDTO request) {
        User currentUser = getCurrentUser(authentication.getName());
        Contact createContact = Contact.builder()
                .user(currentUser)
                .name(request.name())
                .emails(request.emails())
                .phoneNumbers(request.phones())
                .build();
        contactRepo.save(createContact);
        return new ContactRespone(createContact.getId(), "Contact was created successfully!");
    }

    @Override
    public ContactRespone deleteContact(Authentication authentication, Long contactId) {
        User user = getCurrentUser(authentication.getName());
        Contact contact = findById(contactId);
        List<Contact> contacts = contactRepo.findByUser(user);
        if (!contacts.contains(contact)) throw new ForbiddenRequestException();
        contactRepo.deleteById(contactId);
        return new ContactRespone(contactId, "Contact was successfully deleted!");
    }

    private Contact findById(Long contactId) {
        return contactRepo.findById(contactId)
                .orElseThrow(ContactNotFoundException::new);
    }

    private User getCurrentUser(String username) {
        User user = userService.findUserByUsername(username);
        if (isNotCurrentUser(user)) {
            throw new ForbiddenRequestException();
        }
        return user;
    }

    private boolean isNotCurrentUser(User user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !username.equalsIgnoreCase(user.getUsername());
    }
}
