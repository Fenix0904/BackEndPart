package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "images";


    // TODO fix this
    @Override
    public void saveImageFile(Anime anime, MultipartFile poster, HttpServletRequest servletRequest) throws IOException {
        String fileName = poster.getOriginalFilename();
        String path = servletRequest.getServletContext().getRealPath("") + UPLOAD_DIR + File.separator + fileName;
        Files.write(new File(path).toPath(), poster.getBytes());
        anime.setPosterUrl(path);
    }

}
