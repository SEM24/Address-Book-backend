package com.chisoftware.contact.controller;

import com.chisoftware.contact.service.CsvService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
@Tag(name = "IOController", description = "API for Import/Export")
public class IOController {
    private CsvService csvService;

    @GetMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Export contacts")
    public String exportData(Authentication authentication) throws IOException {
        return csvService.exportContactsToJSON(authentication);
    }

    @PutMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Import contacts")
    public String importData(Authentication authentication,
                             @RequestParam("file") MultipartFile multipartFile) {
        return csvService.importContactsFromFile(authentication, multipartFile);
    }
}
