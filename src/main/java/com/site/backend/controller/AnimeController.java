package com.site.backend.controller;

import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import com.site.backend.utils.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value = "/create")
    public ResponseEntity addNewAnime(@RequestBody Anime anime) {
        Anime created = animeService.createNewAnime(anime);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // TODO  too many db queries. Maybe it will be better to implement in-memory 'cache' for created anime, than add poster and than save!

    @PostMapping(value = "/uploadPoster")
    public ResponseEntity addPosterToAnime(@RequestParam("animeId") Long animeId, @RequestParam("poster") MultipartFile poster) {
        Anime animeById = animeService.getAnimeById(animeId);
        try {
            animeById.setPoster(poster.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        animeService.updateAnime(animeById);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public void updateAnime(@RequestBody Anime anime) {
        animeService.updateAnime(anime);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnimeById(@PathVariable Long id) {
        animeService.deleteAnimeById(id);
    }
}
