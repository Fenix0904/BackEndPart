package com.site.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import com.site.backend.service.ImageService;
import com.site.backend.utils.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    private final AnimeService animeService;
    private final ImageService imageService;

    @Autowired
    public AnimeController(AnimeService animeService, ImageService imageService) {
        this.animeService = animeService;
        this.imageService = imageService;
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
    public ResponseEntity addNewAnime(@RequestParam("anime") String animeString, @RequestParam("poster") MultipartFile poster, HttpServletRequest servletRequest) {
        try {
            Anime anime = new ObjectMapper().readValue(animeString, Anime.class);
            imageService.saveImageFile(anime, poster, servletRequest);
            Anime created = animeService.createNewAnime(anime);
            return ResponseEntity.status(HttpStatus.OK).body(created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
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
