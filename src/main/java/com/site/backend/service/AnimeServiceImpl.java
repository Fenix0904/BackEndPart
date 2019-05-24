package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.domain.AnimeSeason;
import com.site.backend.domain.Genre;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;
    private final GenreRepository genreRepository;
    private final SeasonRepository seasonRepository;

    @Autowired
    public AnimeServiceImpl(AnimeRepository animeRepository, GenreRepository genreRepository, SeasonRepository seasonRepository) {
        this.animeRepository = animeRepository;
        this.genreRepository = genreRepository;
        this.seasonRepository = seasonRepository;
    }

    // TODO looks like this method can also perform 'update' logic.
    @Override
    public Anime createNewAnime(Anime newAnime) {
        Set<Genre> attachedGenres = new HashSet<>();
        if (newAnime.getGenres() != null) {
            for (Genre genre : newAnime.getGenres()) {
                Optional<Genre> attached = genreRepository.findById(genre.getId());
                attached.ifPresent(attachedGenres::add);
            }
            newAnime.setGenres(attachedGenres);
        }
        if (newAnime.getAnimeSeason() != null && newAnime.getAnimeSeason().getId() != null) {
            Optional<AnimeSeason> attachedSeason = seasonRepository.findById(newAnime.getAnimeSeason().getId());
            attachedSeason.ifPresent(newAnime::setAnimeSeason);
        } else {
            newAnime.setAnimeSeason(null);
        }

        return animeRepository.save(newAnime);
    }

    @Override
    public Iterable<Anime> getAll() {
        return animeRepository.findAll();
    }


    @Override
    public Anime getAnimeById(Long id) throws AnimeNotFoundException {
        Optional<Anime> anime = animeRepository.findById(id);
        return anime.orElseThrow(AnimeNotFoundException::new);
    }

    @Override
    public void updateAnime(Anime newAnime) {
        animeRepository.save(newAnime);
    }

    @Override
    public void deleteAnimeById(Long id) {
        animeRepository.deleteById(id);
    }
}
