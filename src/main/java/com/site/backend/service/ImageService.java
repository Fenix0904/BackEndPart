package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void addPosterToAnime(Anime anime, MultipartFile file) throws IOException;

    boolean deletePoster(Anime anime);
}
