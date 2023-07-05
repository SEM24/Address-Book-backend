package com.chisoftware.contact.controller;

import com.chisoftware.contact.model.dto.ContactRespone;
import com.chisoftware.contact.model.dto.CreateContactDTO;
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

@RestController
@Tag(name = "Contact Controller", description = "CRUD for Contact Controller")
@AllArgsConstructor
@Validated
@PreAuthorize("hasRole('USER')")
@RequestMapping("/contacts")
public class ContactController {
    private ContactService contactService;

    @Operation(summary = "Add Contact")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactRespone addContact(Authentication authentication,
                                     @RequestBody @Valid CreateContactDTO request) {
        return contactService.addContact(authentication, request);
    }

    @DeleteMapping
    @Operation(summary = "Delete Contact")
    @ResponseStatus(HttpStatus.OK)
    ContactRespone deleteContact(Authentication authentication, @RequestParam("contact-id") Long contactId) {
        return contactService.deleteContact(authentication, contactId);
    }
}
