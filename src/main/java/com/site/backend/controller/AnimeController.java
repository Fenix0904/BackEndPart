package com.site.backend.controller;

import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import com.site.backend.utils.AnimeSearchFilter;
import com.site.backend.utils.ErrorsCollector;
import com.site.backend.utils.ResponseError;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import com.site.backend.utils.exceptions.ContentNotAllowedException;
import com.site.backend.utils.exceptions.PosterException;
import com.site.backend.validator.AnimeCreationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    private final AnimeService animeService;
    private final AnimeCreationValidator validator;

    @Autowired
    public AnimeController(AnimeService animeService, AnimeCreationValidator validator) {
        this.animeService = animeService;
        this.validator = validator;
    }

    @PostMapping(value = "/create")
    public ResponseEntity addNewAnime(Anime anime,
                                      BindingResult bindingResult,
                                      @RequestPart(value = "poster", required = false) MultipartFile poster)
            throws ContentNotAllowedException, PosterException {
        if (anime == null) {
            throw new ContentNotAllowedException();
        }
        validator.validate(anime, bindingResult);
        if (bindingResult.getErrorCount() > 1) {
            List<ResponseError> errors = ErrorsCollector.collectErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Anime created = animeService.createNewAnime(anime, poster);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PutMapping(value = "/update")
    public void updateAnime(@RequestBody Anime anime,
                            BindingResult bindingResult,
                            @RequestPart(value = "poster", required = false) MultipartFile poster)
            throws ContentNotAllowedException, PosterException {
        if (anime == null) {
            throw new ContentNotAllowedException();
        }
        validator.validate(anime, bindingResult);
        animeService.updateAnime(anime, poster);
    }

    @GetMapping("/")
    public Iterable getAll() {
        return animeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimeById(@PathVariable Long id) throws AnimeNotFoundException, ContentNotAllowedException {
        if (id < 0) {
            throw new ContentNotAllowedException();
        }
        Anime anime = animeService.getAnimeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(anime);
    }

    @PostMapping("/filter")
    public ResponseEntity getAnimeBy(@RequestBody AnimeSearchFilter filter) {
        Iterable<Anime> animes = animeService.getAnimesBy(filter);
        return ResponseEntity.status(HttpStatus.OK).body(animes);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnimeById(@PathVariable Long id) {
        animeService.deleteAnimeById(id);
    }
}
