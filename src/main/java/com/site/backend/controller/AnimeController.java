package com.site.backend.controller;

import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import com.site.backend.utils.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animes")
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
    public ResponseEntity getAnimeById(@PathVariable Long id) {
        Anime anime = animeService.getAnimeById(id);
        if (anime == null) {
            ResponseError error = new ResponseError("id", "There are no anime with such id!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.status(HttpStatus.OK).body(anime);
    }

    @PostMapping("/create")
    public ResponseEntity addNewAnime(@RequestBody Anime anime) {
        animeService.createNewAnime(anime);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public void updateAnime(@RequestBody Anime anime) {
        animeService.updateAnime(anime);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnimeById(@PathVariable Long id) {
        animeService.deleteAnimeById(id);
    }
}
