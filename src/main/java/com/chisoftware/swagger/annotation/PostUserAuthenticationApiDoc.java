package com.chisoftware.swagger.annotation;

import com.chisoftware.user.model.dto.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "User Authentication",
        description = "As a guest I want to have option to register on website.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                description = "CREATED",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = Token.class))),
        @ApiResponse(responseCode = "409",
                description = "CONFLICT (user with login already exists)",
                content = @Content),
        @ApiResponse(
                responseCode = "400",
                description = "BAD_REQUEST",
                content = @Content),
})
public @interface PostUserAuthenticationApiDoc {
}