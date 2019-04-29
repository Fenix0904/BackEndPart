package com.site.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Season season;

    @Enumerated
    private AnimeType type;

    private Integer episodesCount;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
    private Byte[] poster;
}
