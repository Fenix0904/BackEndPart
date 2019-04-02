package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;

    @Autowired
    public AnimeServiceImpl(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public void createNewAnime(Anime newAnime) {
        animeRepository.save(newAnime);
    }

    @Override
    public Iterable<Anime> getAll() {
        return animeRepository.getAllEagerly();
    }

    @Override
    public Anime getAnimeById(Long id) {
        return animeRepository.getByIdEagerly(id);
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
