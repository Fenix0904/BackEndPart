package com.site.backend.controller;

import com.site.backend.domain.Genre;
import com.site.backend.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("animes")
public class GenreController {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    public Set<Genre> getAllGenres() {
        List<Genre> all = genreRepository.findAll();
        all.sort(Comparator.comparing(Genre::getGenre));
        return new LinkedHashSet<>(all);
    }
}
