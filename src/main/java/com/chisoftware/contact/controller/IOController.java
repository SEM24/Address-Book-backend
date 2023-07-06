package com.chisoftware.contact.controller;

import com.chisoftware.contact.service.CsvService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class IOController {
    private CsvService csvService;

    @GetMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    public String getAllEmployeesInCsv(Authentication authentication) throws IOException {
        return csvService.exportContactsToJSON(authentication);
    }

    @PutMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public String importData(Authentication authentication,
                             @RequestParam("file") MultipartFile multipartFile) {
        return csvService.importContactsFromFile(authentication, multipartFile);
    }
}
