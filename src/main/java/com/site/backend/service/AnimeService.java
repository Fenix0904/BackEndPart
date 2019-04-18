package com.site.backend.service;

import com.site.backend.domain.Anime;

public interface AnimeService {

    Anime createNewAnime(Anime newAnime);

    Iterable<Anime> getAll();

    Anime getAnimeById(Long id);

    void updateAnime(Anime newAnime);

    void deleteAnimeById(Long id);

}
