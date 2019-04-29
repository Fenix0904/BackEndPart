package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private AnimeService animeService;

    @Autowired
    public ImageServiceImpl(AnimeService animeService) {
        this.animeService = animeService;
    }

    @Override
    public void addPosterToAnime(Anime anime, MultipartFile poster) {
        try {
            Byte[] posterBytes = new Byte[poster.getBytes().length];
            int i = 0;
            for (byte b : poster.getBytes()) {
                posterBytes[i++] = b;
            }

            anime.setPoster(posterBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePoster(Long animeId, MultipartFile poster) throws AnimeNotFoundException {
        try {
            Byte[] posterBytes = new Byte[poster.getBytes().length];
            int i = 0;
            for (byte b : poster.getBytes()) {
                posterBytes[i++] = b;
            }

            Anime anime = animeService.getAnimeById(animeId);

            anime.setPoster(posterBytes);

            animeService.updateAnime(anime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
