package com.chisoftware.swagger.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Add Contact for User",
        description = "As a user I want to add contacts to my phone book.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "SUCCESS"),
        @ApiResponse(responseCode = "404",
                description = "NOT FOUND",
                content = @Content),
        @ApiResponse(
                responseCode = "401",
                description = "UNAUTHORIZED",
                content = @Content),
        @ApiResponse(
                responseCode = "403",
                description = "FORBIDDEN (if not the owner wants to see the contacts)",
                content = @Content),
        @ApiResponse(
                responseCode = "409",
                description = "CONFLICT (Duplicate are not allowed)",
                content = @Content)
})
public @interface PostAddContactApiDoc {
}