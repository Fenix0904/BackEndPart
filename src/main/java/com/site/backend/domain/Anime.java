package com.site.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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

    private String posterUrl;
}
