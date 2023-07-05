package com.chisoftware.contact;

import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser(User user);
}