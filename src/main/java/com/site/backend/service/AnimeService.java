package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.utils.AnimeSearchFilter;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import com.site.backend.utils.exceptions.PosterException;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface AnimeService {

    Anime createNewAnime(Anime newAnime, @Nullable MultipartFile poster) throws PosterException;

    Iterable<Anime> getAll();

    Anime getAnimeById(Long id) throws AnimeNotFoundException;

    Iterable<Anime> getAnimesBy(AnimeSearchFilter filter);

    void updateAnime(Anime newAnime, @Nullable MultipartFile poster) throws PosterException;

    void deleteAnimeById(Long id);
}
