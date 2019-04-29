package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.utils.exceptions.AnimeNotFoundException;

public interface AnimeService {

    Anime createNewAnime(Anime newAnime);

    Iterable<Anime> getAll();

    Anime getAnimeByIdEagerly(Long id) throws AnimeNotFoundException;

    Anime getAnimeById(Long id) throws AnimeNotFoundException;

    void updateAnime(Anime newAnime);

    void deleteAnimeById(Long id);

}
