package com.site.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

// TODO try DTOs

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(name = "graph.Anime.genres",
        attributeNodes = @NamedAttributeNode(value = "genres", subgraph = "genres"),
        subgraphs = @NamedSubgraph(name = "genres", attributeNodes = @NamedAttributeNode("animes")))
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private Date year;

    private String type;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "anime_genre",
               joinColumns = @JoinColumn(name = "anime_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "anime_staff",
               joinColumns = @JoinColumn(name = "anime_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> staff;

    @Lob
    private Byte[] image;
}
