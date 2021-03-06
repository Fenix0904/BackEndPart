package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Profile("local-storage")
public class ImageServiceImpl implements ImageService {

    private String uploadPath;

    public ImageServiceImpl(@Value("${upload.path}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void uploadPoster(Anime anime, MultipartFile poster) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFileName = UUID.randomUUID().toString();
        String resultFileName = uuidFileName + "." + poster.getOriginalFilename();

        poster.transferTo(new File(uploadPath + "/" + resultFileName));

        anime.setPoster(resultFileName);
    }

    @Override
    public boolean deletePoster(Anime anime) {
        File poster = new File(uploadPath + "/" + anime.getPoster());
        return poster.exists() && poster.delete();
    }
}
