package com.site.backend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "staff")
    private List<Anime> animes;
}
