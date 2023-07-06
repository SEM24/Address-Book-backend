package com.chisoftware.contact;

import com.chisoftware.contact.model.entity.Contact;
import com.chisoftware.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByNameEqualsIgnoreCaseAndUser(@Param("name") String name, @Param("user") User user);

    List<Contact> findByUser(User user);
}