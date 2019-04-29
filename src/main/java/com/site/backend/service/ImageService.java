package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ImageService {
    void addPosterToAnime(Anime anime, MultipartFile file);

    void savePoster(Long animeId, MultipartFile file) throws AnimeNotFoundException;
}
