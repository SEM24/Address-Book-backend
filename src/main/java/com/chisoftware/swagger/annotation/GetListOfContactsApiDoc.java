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
@Operation(summary = "Get All Contacts of User")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "SUCCESS"),
        @ApiResponse(
                responseCode = "401",
                description = "UNAUTHORIZED",
                content = @Content),
        @ApiResponse(
                responseCode = "403",
                description = "FORBIDDEN (if not the owner wants to see the contacts)",
                content = @Content),
        @ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = @Content)
})
public @interface GetListOfContactsApiDoc {
}