package com.chisoftware.contact.mapper;

import com.chisoftware.contact.model.dto.ContactDTO;
import com.chisoftware.contact.model.dto.ContactShort;
import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {

    ContactDTO toContactDTO(Contact contact);

    default Contact toContact(ContactShort contact, User user) {
        return Contact.builder()
                .name(contact.name())
                .user(user)
                .emails(new HashSet<>(contact.emails()))
                .phones(new HashSet<>(contact.phones()))
                .build();
    }

    default ContactShort toContactExport(Contact contact) {
        return ContactShort.builder()
                .name(contact.getName())
                .phones(contact.getPhones().stream().toList())
                .emails(contact.getEmails().stream().toList())
                .build();
    }
}
