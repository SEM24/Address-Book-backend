package com.chisoftware.user.controller;

import com.chisoftware.swagger.annotation.PostUserAuthenticationApiDoc;
import com.chisoftware.swagger.annotation.PostUserLoginApiDoc;
import com.chisoftware.user.model.dto.RegistrationDTO;
import com.chisoftware.user.model.dto.Token;
import com.chisoftware.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API for User Authentication")
public class UserController {
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PostUserAuthenticationApiDoc
    public Token registration(@Valid @RequestBody RegistrationDTO request) {
        return userService.authentication(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @PostUserLoginApiDoc
    public Token signIn(@Valid Authentication authentication) {
        return userService.signIn(authentication.getName());
    }
}
