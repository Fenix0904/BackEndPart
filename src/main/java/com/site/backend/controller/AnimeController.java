package com.site.backend.controller;

import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/")
    public Iterable getAll() {
        return animeService.getAll();
    }

    @GetMapping("/{id}")
    public Anime getAnimeById(@PathVariable Long id) {
        return animeService.getAnimeById(id);
    }

    @PostMapping("/create")
    public void addNewAnime(@RequestBody Anime newAnime) {
        animeService.createNewAnime(newAnime);
    }

    @PutMapping("/update")
    public void updateAnime(@RequestBody Anime updatedAnime) {
        animeService.updateAnime(updatedAnime);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnimById(@PathVariable Long id) {
        animeService.deleteAnimeById(id);
    }
}
