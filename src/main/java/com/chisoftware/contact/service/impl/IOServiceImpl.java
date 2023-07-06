package com.chisoftware.contact.service.impl;

import com.chisoftware.contact.ContactRepository;
import com.chisoftware.contact.mapper.ContactMapper;
import com.chisoftware.contact.model.dto.ContactShort;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.contact.service.ContactService;
import com.chisoftware.contact.service.CsvService;
import com.chisoftware.user.UserRepository;
import com.chisoftware.user.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IOServiceImpl implements CsvService {
    private ContactRepository contactRepo;
    private ContactService contactService;
    private final UserRepository userRepository;
    private ContactMapper contactMapper;
    private ObjectMapper objectMapper;

    @Override
    public String exportContactsToJSON(Authentication authentication) throws IOException {
        User currentUser = contactService.getCurrentUser(authentication.getName());

        List<ContactShort> contacts = new ArrayList<>(contactRepo.findByUser(currentUser)
                .stream()
                .map(contactMapper::toContactExport)
                .toList());

        String fileName = "contacts.json";
        Path filePath = Path.of("data", fileName);
        Files.createDirectories(filePath.getParent());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), contacts);
        return "The contacts was successfully exported!";
    }

    @Override
    public String importContactsFromFile(Authentication authentication, MultipartFile multipartFile) {
        User user = contactService.getCurrentUser(authentication.getName());
        if (multipartFile.isEmpty()) {
            // Handle the case when no file is uploaded
            return "No file uploaded.";
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            List<ContactShort> contacts = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            List<Contact> importedContacts = contacts.stream()
                    .map(contact -> contactMapper.toContact(contact, user))
                    .toList();
            contactRepo.saveAll(importedContacts);
            user.getContacts().addAll(importedContacts);
            userRepository.save(user);
            return "Data imported successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred during data import.";
        }
    }
}