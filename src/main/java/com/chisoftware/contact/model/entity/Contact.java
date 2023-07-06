package com.chisoftware.contact.model.entity;

import com.chisoftware.additional.model.entity.ImageData;
import com.chisoftware.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
//    private ImageData image;

    private String name;

    @ElementCollection
    private Set<String> emails;

    @ElementCollection
    private Set<String> phones;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
