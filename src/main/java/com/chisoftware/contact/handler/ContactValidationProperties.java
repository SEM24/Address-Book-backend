package com.chisoftware.contact.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "contactvalidation")
public record ContactValidationProperties(String emailRegex) {
}