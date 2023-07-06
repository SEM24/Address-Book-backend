package com.chisoftware.contact.controller;

import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactResponse;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.contact.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Contact Controller", description = "CRUD for Contact Controller")
@AllArgsConstructor
@Validated
@PreAuthorize("hasRole('USER')")
@RequestMapping("/contacts")
public class ContactController {
    private ContactService contactService;

    @Operation(summary = "Show Contacts")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> showContacts(Authentication authentication) {
        return contactService.getAllContacts(authentication);
    }

    @Operation(summary = "Add Contact")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse addContact(Authentication authentication,
                                      @RequestBody @Valid ContactDTO request) {
        return contactService.addContact(authentication, request);
    }

    @DeleteMapping("/{contact-id}")
    @Operation(summary = "Delete Contact")
    @ResponseStatus(HttpStatus.OK)
    ContactResponse deleteContact(Authentication authentication, @PathVariable("contact-id") Long contactId) {
        return contactService.deleteContact(authentication, contactId);
    }

    @PutMapping("/{contact-id}")
    @Operation(summary = "Edit Contact")
    @ResponseStatus(HttpStatus.OK)
    ContactResponse editContact(Authentication authentication,
                                @PathVariable("contact-id") Long contactId,
                                @RequestBody @Valid ContactDTO request) {
        return contactService.editContact(authentication, contactId, request);
    }
}
