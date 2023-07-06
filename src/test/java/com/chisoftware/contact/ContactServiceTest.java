package com.chisoftware.contact;

import com.chisoftware.contact.handler.annotation.ValidEmail;
import com.chisoftware.contact.handler.annotation.ValidPhone;
import com.chisoftware.contact.mapper.ContactMapper;
import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactResponse;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.contact.service.impl.ContactServiceImpl;
import com.chisoftware.user.handler.exception.ForbiddenRequestException;
import com.chisoftware.user.model.entity.Role;
import com.chisoftware.user.model.entity.User;
import com.chisoftware.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private ContactRepository contactRepo;
    @Mock
    ContactMapper contactMapper;
    @InjectMocks
    private ContactServiceImpl contactService;
    private User user;
    private Contact contact;

    @BeforeEach
    public void create() {
        user = User.builder()
                .id(1L)
                .username("Vlad")
                .password("vladkharchenko_123")
                .roles(Set.of(Role.USER.getAuthority()))
                .build();
        contact = Contact.builder()
                .id(1L)
                .user(user)
                .name("Test Name")
                .phones(Set.of())
                .emails(Set.of())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Add Contact")
    void given_RequestAndUser_When_AddContact_Then_ReturnResponse() {
        Authentication authentication = mock(Authentication.class);
        //Create the record
        List<@ValidEmail String> emails = Collections.singletonList("test@example.com");
        List<@ValidPhone String> phones = Collections.singletonList("1234567890");
        ContactDTO request = ContactDTO.builder()
                .name("Test Name")
                .emails(emails)
                .phones(phones)
                .build();

        //Mock the behavior of findUserByUsername
        when(authentication.getName()).thenReturn("Vlad");
        User currentUser = User.builder()
                .id(1L)
                .username("Vlad")
                .password("vladkharchenko_123")
                .roles(Set.of(Role.USER.getAuthority()))
                .build();
        when(userService.findUserByUsername(authentication.getName())).thenReturn(currentUser);

        //Mock the behavior of contactRepo.save to return the saved contact
        Contact savedContact = Contact.builder()
                .id(1L)
                .user(currentUser)
                .name(request.name())
                .emails(new HashSet<>(request.emails()))
                .phones(new HashSet<>(request.phones()))
                .build();
        when(contactRepo.save(any(Contact.class))).thenReturn(savedContact);

        //Invoke the method
        ContactResponse response = contactService.addContact(authentication, request);

        //Verify that the methods were called and the contact was saved correctly
        verify(authentication, times(2)).getName();
        verify(userService).findUserByUsername(authentication.getName());
        verify(contactRepo).save(any(Contact.class));

        //Assert the values
        assertEquals("Contact was created successfully!", response.status());
    }

    @Test
    @Order(2)
    @DisplayName("Edit Contact")
    void given_Authentication_ContactId_Request_When_EditContact_Then_ReturnResponse() {
        // Mock authentication and contact data
        Authentication authentication = mock(Authentication.class);
        Long contactId = 1L;
        ContactDTO request = ContactDTO.builder()
                .name("New Name")
                .emails(Collections.singletonList("new@example.com"))
                .phones(Collections.singletonList("9876543210"))
                .build();

        //Mock the method calls
        when(contactRepo.findById(contact.getId())).thenReturn(Optional.ofNullable(contact));
        when(userService.findUserByUsername(authentication.getName())).thenReturn(user);
        when(userService.isNotCurrentUser(user)).thenReturn(false);
        List<Contact> userContacts = Collections.singletonList(contact);
        when(contactRepo.findByUser(user)).thenReturn(userContacts);

        //Invoke the method
        ContactResponse response = contactService.editContact(authentication, contactId, request);

        //Verify that the methods
        verify(contactRepo).findById(contact.getId());
        verify(userService).findUserByUsername(authentication.getName());
        verify(userService).isNotCurrentUser(user);
        verify(contactRepo).save(contact);

        //Assert the response
        assertEquals(contactId, response.id());
        assertEquals("Edited successfully!", response.status());
        assertEquals("New Name", contact.getName());
        assertEquals(Collections.singleton("new@example.com"), contact.getEmails());
        assertEquals(Collections.singleton("9876543210"), contact.getPhones());
    }

    @Test
    @Order(3)
    @DisplayName("Get All Contacts")
    void given_User_When_GetAllContacts_Then_ReturnContacts() {
        Authentication authentication = mock(Authentication.class);
        User user = User.builder()
                .id(1L)
                .username("Vlad")
                .password("vladkharchenko_123")
                .roles(Set.of(Role.USER.getAuthority()))
                .contacts(List.of(
                        Contact.builder().id(1L).name("Contact 1").build(),
                        Contact.builder().id(2L).name("Contact 2").build()
                ))
                .build();

        //Mock the method calls
        when(userService.findUserByUsername(authentication.getName())).thenReturn(user);

        //Define the expected contact DTOs
        List<ContactDTO> expectedContacts = List.of(
                ContactDTO.builder().name("Contact 1").build(),
                ContactDTO.builder().name("Contact 2").build()
        );

        //Mock the behavior of contactMapper
        when(contactMapper.toContactDTO(any(Contact.class))).thenAnswer(invocation -> {
            Contact contact = invocation.getArgument(0);
            return ContactDTO.builder().name(contact.getName()).build();
        });

        //Invoke the method
        List<ContactDTO> contacts = contactService.getAllContacts(authentication);

        //Verify that the method was called and the results match the expected contacts
        verify(userService).findUserByUsername(authentication.getName());
        assertEquals(expectedContacts.size(), contacts.size());
        assertEquals(expectedContacts.get(0).name(), contacts.get(0).name());
        assertEquals(expectedContacts.get(1).name(), contacts.get(1).name());
    }

    @Test
    @Order(4)
    @DisplayName("Delete Contact")
    void given_Contact_When_Delete_Then_Response() {
        Authentication authentication = mock(Authentication.class);

        //Mock the method calls
        when(contactRepo.findById(contact.getId())).thenReturn(Optional.ofNullable(contact));
        when(userService.findUserByUsername(authentication.getName())).thenReturn(user);
        //Return false to simulate that it's current user
        when(userService.isNotCurrentUser(user)).thenReturn(false);
        List<Contact> userContacts = Collections.singletonList(contact);
        when(contactRepo.findByUser(user)).thenReturn(userContacts);

        //Invoke the method
        ContactResponse response = contactService.deleteContact(authentication, 1L);

        //Verify that the methods were called and the response is as expected
        verify(contactRepo).findById(contact.getId());
        verify(userService).findUserByUsername(authentication.getName());
        verify(userService).isNotCurrentUser(user);
        verify(contactRepo).deleteById(contact.getId());
        assertEquals(contact.getId(), response.id());
        assertEquals("Contact was successfully deleted!", response.status());
    }


    @Test
    @Order(5)
    @DisplayName("Check If Contact is Not Owned")
    void given_ContactAndUser_When_CheckIfNotOwnContact_Then_ThrowForbiddenRequestException() {
        List<Contact> contacts = new ArrayList<>();

        //Add contacts to the list
        contacts.add(mock(Contact.class));
        contacts.add(mock(Contact.class));

        //Set up the behavior of the contactRepo mock
        when(contactRepo.findByUser(user)).thenReturn(contacts);

        //Assert that no exception is thrown when the contact is in the list
        assertDoesNotThrow(() -> contactService.checkIfNotOwnContact(user, contacts.get(0)));

        //Assert that a ForbiddenRequestException is thrown
        assertThrows(ForbiddenRequestException.class, () -> contactService.checkIfNotOwnContact(user, contact));
    }

    @Test
    @Order(6)
    @DisplayName("Check if not Current User")
    void given_Talent_WhenIsNotCurrentUser_Then_ThrowForbiddenRequestException() {
        when(userService.isNotCurrentUser(user)).thenThrow(ForbiddenRequestException.class);
        assertThrows(ForbiddenRequestException.class, () -> {
            userService.isNotCurrentUser(user);
        });
    }
}