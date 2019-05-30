package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.domain.AnimeSeason;
import com.site.backend.domain.Genre;
import com.site.backend.domain.User;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.repository.UserRepository;
import com.site.backend.utils.AnimeSearchFilter;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import com.site.backend.utils.exceptions.PosterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AnimeServiceImpl implements AnimeService {

    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;
    private final GenreRepository genreRepository;
    private final SeasonRepository seasonRepository;
    private final ImageService imageService;

    @Autowired
    public AnimeServiceImpl(AnimeRepository animeRepository,
                            GenreRepository genreRepository,
                            SeasonRepository seasonRepository,
                            ImageService imageService,
                            UserRepository userRepository) {
        this.animeRepository = animeRepository;
        this.genreRepository = genreRepository;
        this.seasonRepository = seasonRepository;
        this.imageService = imageService;
        this.userRepository = userRepository;
    }

    @Override
    public Anime createNewAnime(Anime newAnime, MultipartFile poster) throws PosterException {
        if (newAnime.getGenres() != null) {
            Set<Genre> attachedGenres = new HashSet<>();
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

        if (newAnime.getStaff() != null) {
            Set<User> attachedStaff = new HashSet<>();
            for (User user : newAnime.getStaff()) {
                User attached = userRepository.findByUsername(user.getUsername());
                attachedStaff.add(attached);
            }
            newAnime.setStaff(attachedStaff);
        }

        if (poster != null) {
            try {
                imageService.addPosterToAnime(newAnime, poster);
            } catch (IOException e) {
                throw new PosterException();
            }
        }

        return animeRepository.save(newAnime);
    }

    @Override
    public void updateAnime(Anime newAnime, MultipartFile poster) throws PosterException {
        Optional<Anime> optional = animeRepository.findById(newAnime.getId());
        Lock lock = new ReentrantLock(true);
        // TODO: try to implement try lock and return something if someone is updating anime
        lock.lock();
        if (optional.isPresent()) {
            Anime repoAnime = optional.get();
            if (!StringUtils.isEmpty(newAnime.getTitle())) {
                repoAnime.setTitle(newAnime.getTitle());
            }
            if (!StringUtils.isEmpty(newAnime.getDescription())) {
                repoAnime.setDescription(newAnime.getDescription());
            }
            if (newAnime.getType() != null) {
                repoAnime.setType(newAnime.getType());
            }
            // TODO: now I assume that received genres has ids. But what if they doesn't?
            if (newAnime.getGenres() != null) {
                repoAnime.setGenres(newAnime.getGenres());
            }
            if (newAnime.getAnimeSeason() != null) {
                repoAnime.setAnimeSeason(newAnime.getAnimeSeason());
            }
            if (newAnime.getEpisodesCount() != null) {
                repoAnime.setEpisodesCount(newAnime.getEpisodesCount());
            }
            // TODO: same as with genres
            if (newAnime.getStaff() != null) {
                repoAnime.setStaff(newAnime.getStaff());
            }
            if (poster != null) {
                try {
                    imageService.addPosterToAnime(repoAnime, poster);
                } catch (IOException e) {
                    throw new PosterException();
                }
            }
            animeRepository.save(repoAnime);
        }
        lock.unlock();
    }

    @Override
    public void deleteAnimeById(Long id) {
        animeRepository.deleteById(id);
    }

    @Override
    public Iterable<Anime> getAnimesBy(AnimeSearchFilter filter) {
        Set<Anime> animes = animeRepository.getAnimeByFilterParameters(filter);
        if (animes == null) {
            return animeRepository.findAll();
        }
        return animes;
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
}
