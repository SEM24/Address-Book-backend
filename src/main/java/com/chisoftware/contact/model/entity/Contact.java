package com.chisoftware.contact.model.entity;

import com.chisoftware.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "contacts")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private Set<String> emails;

    @ElementCollection
    private Set<String> phoneNumbers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
