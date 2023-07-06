package com.chisoftware.user.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uservalidation")
public record UserValidationProperties(String textRegex) {
}