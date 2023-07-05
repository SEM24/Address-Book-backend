package com.chisoftware.contact.service;

import com.chisoftware.contact.ContactRepository;
import com.chisoftware.contact.handler.exception.AlreadyExistsException;
import com.chisoftware.contact.handler.exception.ContactNotFoundException;
import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactRespone;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.user.handler.exception.BadRequestException;
import com.chisoftware.user.handler.exception.ForbiddenRequestException;
import com.chisoftware.user.model.entity.User;
import com.chisoftware.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepo;
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAllContacts(Authentication authentication) {
        User user = getCurrentUser(authentication.getName());
        return user.getContacts();
    }

    @Override
    public ContactRespone addContact(Authentication authentication, ContactDTO request) {
        User currentUser = getCurrentUser(authentication.getName());
        validateRequest(request, currentUser);
        Contact createContact = Contact.builder()
                .user(currentUser)
                .name(request.name())
                .emails(new HashSet<>(request.emails()))
                .phones(new HashSet<>(request.phones()))
                .build();
        contactRepo.save(createContact);
        return new ContactRespone(createContact.getId(), "Contact was created successfully!");
    }

    @Override
    public ContactRespone editContact(Authentication authentication, Long contactId, ContactDTO request) {
        User currentUser = getCurrentUser(authentication.getName());
        Contact contact = findById(contactId);
        checkIfNotOwnContact(currentUser, contact);

        updateContactFields(contact, request, currentUser);
        contactRepo.save(contact);

        return new ContactRespone(contact.getId(), "Edited successfully!");
    }

    @Override
    public ContactRespone deleteContact(Authentication authentication, Long contactId) {
        User user = getCurrentUser(authentication.getName());
        Contact contact = findById(contactId);
        checkIfNotOwnContact(user, contact);
        contactRepo.deleteById(contactId);
        return new ContactRespone(contactId, "Contact was successfully deleted!");
    }

    /*
     * Check the request fields and use a condition to handle the situation when the request fields are empty
     * (since a PUT mapping can receive specific amount of fields).
     */

    private void updateContactFields(Contact contact, ContactDTO request, User currentUser) {
        if (request.name() != null && !request.name().isEmpty()) {
            checkIfContactNameNotUnique(request, currentUser);
            contact.setName(request.name());
        }
        if (request.phones() != null && !request.phones().isEmpty()) {
            Set<String> uniquePhones = new HashSet<>(request.phones());
            if (uniquePhones.size() != request.phones().size()) {
                throw new AlreadyExistsException("Duplicate phone are not allowed.");
            }
            contact.setPhones(uniquePhones);
        }
        if (request.emails() != null && !request.emails().isEmpty()) {
            Set<String> uniqueEmails = new HashSet<>(request.emails());
            if (uniqueEmails.size() != request.emails().size()) {
                throw new AlreadyExistsException("Duplicate email are not allowed.");
            }
            contact.setEmails(uniqueEmails);
        }
    }

    private void checkIfContactNameNotUnique(ContactDTO request, User currentUser) {
        //Contact names must be unique for current user, but not for others
        if (contactRepo.existsByNameContainsIgnoreCaseAndUser(request.name(), currentUser))
            throw new AlreadyExistsException("Contact names must be unique for each contact.");
    }
    /*
     * Check if the fields in the list are not duplicates of each other.
     */
    private void validateRequest(ContactDTO request, User currentUser) {
        //Check that fields are not null in request.
        if (request.name() == null || request.emails() == null || request.phones() == null)
            throw new BadRequestException("The fields mustn't be empty.");
        checkIfContactNameNotUnique(request, currentUser);

        // Validate unique emails
        Set<String> uniqueEmails = new HashSet<>(request.emails());
        if (uniqueEmails.size() != request.emails().size()) {
            throw new AlreadyExistsException("Duplicate email are not allowed.");
        }
        // Validate unique phones
        Set<String> uniquePhones = new HashSet<>(request.phones());
        if (uniquePhones.size() != request.phones().size()) {
            throw new AlreadyExistsException("Duplicate phone are not allowed.");
        }
    }


    private User getCurrentUser(String username) {
        User user = userService.findUserByUsername(username);
        if (isNotCurrentUser(user)) {
            throw new ForbiddenRequestException();
        }
        return user;
    }

    private void checkIfNotOwnContact(User user, Contact contact) {
        List<Contact> contacts = contactRepo.findByUser(user);
        if (!contacts.contains(contact)) throw new ForbiddenRequestException();
    }

    private Contact findById(Long contactId) {
        return contactRepo.findById(contactId)
                .orElseThrow(ContactNotFoundException::new);
    }

    private boolean isNotCurrentUser(User user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !username.equalsIgnoreCase(user.getUsername());
    }
}
