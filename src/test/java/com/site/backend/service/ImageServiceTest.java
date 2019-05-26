package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    private ImageService imageService;

    @Value("${upload.path}")
    private String uploadPath;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl();
    }

    @Test
    public void saveImageFileWithAnime() throws Exception {
        //given
        MultipartFile file = new MockMultipartFile("poster", "poster.txt", "text/plain", "This is poster".getBytes());
        Anime anime = new Anime();

        //when
        imageService.addPosterToAnime(anime, file);

        //then
//        assertEquals(file.getBytes().length, anime.getPoster().length);
    }

}