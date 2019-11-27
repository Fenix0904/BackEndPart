package com.site.backend.unit.service;

import com.site.backend.domain.Anime;
import com.site.backend.service.ImageService;
import com.site.backend.service.ImageServiceImpl;
import org.hamcrest.core.StringEndsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ImageServiceTest {

    private ImageService imageService;

    @Rule
    public final TemporaryFolder testRoot = new TemporaryFolder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(testRoot.getRoot().getPath());
    }

    @Test
    public void saveImageFileWithAnime() throws Exception {
        //given
        MultipartFile file = new MockMultipartFile("poster", "poster.txt", "text/plain", "This is poster".getBytes());
        Anime anime = new Anime();

        //when
        imageService.uploadPoster(anime, file);

        //then
        assertNotNull(anime.getPoster());
        assertThat(anime.getPoster(), StringEndsWith.endsWith(file.getOriginalFilename()));
    }

    @Test
    public void deleteFileWithAnimeDeletion() throws IOException {
        //given
        MultipartFile file = new MockMultipartFile("poster", "poster.txt", "text/plain", "This is poster".getBytes());
        Anime anime = new Anime();
        imageService.uploadPoster(anime, file);

        //when
        imageService.deletePoster(anime);

        //then
        assertThat(Objects.requireNonNull(new File(testRoot.getRoot().getPath()).list()).length, is(0));
    }

}