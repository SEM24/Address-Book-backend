package com.chisoftware.contact.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvService {
    String exportContactsToJSON(Authentication authentication) throws IOException;

    String importContactsFromFile(Authentication authentication, MultipartFile filePath);
}
