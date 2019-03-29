package com.site.backend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private Date year;

    private String type;

    @ManyToMany
    @JoinTable(name = "anime_genre",
               joinColumns = @JoinColumn(name = "anime_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "anime_staff",
               joinColumns = @JoinColumn(name = "anime_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> staff;

    @Lob
    private Byte[] image;
}
