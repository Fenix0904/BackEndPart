package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ImageService {
    void saveImageFile(Anime anime, MultipartFile file, HttpServletRequest servletRequest) throws IOException;
}
