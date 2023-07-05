package com.chisoftware.user.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "validation")
public record ValidationProperties(String textRegex) {
}