package com.chisoftware.swagger.annotation;

import com.chisoftware.contact.model.dto.ContactResponse;
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
@Operation(summary = "Edit information about contact",
        description = "As a user I want to have an opportunity to edit my personal contacts.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "SUCCESS",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ContactResponse.class))),
        @ApiResponse(responseCode = "404",
                description = "NOT FOUND",
                content = @Content),
        @ApiResponse(
                responseCode = "401",
                description = "UNAUTHORIZED",
                content = @Content),
        @ApiResponse(
                responseCode = "403",
                description = "FORBIDDEN (if not the owner wants to edit the contacts)",
                content = @Content),
        @ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = @Content)
})
public @interface PutEditContactApiDoc {
}