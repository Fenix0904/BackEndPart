package com.site.backend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String genre;

    @ManyToMany(mappedBy = "genres")
    private Set<Anime> animes;
}
