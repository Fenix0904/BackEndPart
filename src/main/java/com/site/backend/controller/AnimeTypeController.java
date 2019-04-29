package com.site.backend.controller;

import com.site.backend.domain.AnimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animes")
public class AnimeTypeController {

    @GetMapping("/types")
    public AnimeType[] getAnimeTypes() {
        return AnimeType.values();
    }
}
