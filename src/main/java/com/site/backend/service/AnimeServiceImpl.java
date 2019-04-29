package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;

    @Autowired
    public AnimeServiceImpl(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public Anime createNewAnime(Anime newAnime) {
        return animeRepository.save(newAnime);
    }

    @Override
    public Iterable<Anime> getAll() {
        return animeRepository.getAllEagerly();
    }

    @Override
    public Anime getAnimeByIdEagerly(Long id) throws AnimeNotFoundException {
        Anime anime = animeRepository.getByIdEagerly(id);
        if (anime == null) {
            throw new AnimeNotFoundException();
        }
        return anime;
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
