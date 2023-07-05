package com.chisoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class PhoneContactsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhoneContactsApplication.class, args);
    }
}
