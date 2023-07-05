package com.chisoftware.user.controller;

import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.model.dto.Token;
import com.chisoftware.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication", description = "API for User Authentication")
@AllArgsConstructor
@Validated
@RequestMapping("/auth")
public class UserController {
    private UserService userService;

    @Operation(summary = "User Authentication")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Token registration(@Valid @RequestBody RegistrationDTO request) {
        return userService.authentication(request);
    }

    @Operation(summary = "User Login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Token signIn(@Valid Authentication authentication) {
        return userService.signIn(authentication.getName());
    }
}
