package com.site.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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

    private String description;

    @OneToOne
    private AnimeSeason animeSeason;

    @Enumerated(EnumType.STRING)
    private AnimeType type;

    private Integer episodesCount;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String poster;
}
