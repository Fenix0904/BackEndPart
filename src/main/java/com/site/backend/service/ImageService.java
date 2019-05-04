package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void addPosterToAnime(Anime anime, MultipartFile file) throws IOException;

    void savePoster(Long animeId, MultipartFile file) throws AnimeNotFoundException, IOException;
}
