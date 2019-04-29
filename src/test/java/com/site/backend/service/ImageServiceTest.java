package com.site.backend.service;

import com.site.backend.domain.Anime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {
    @Mock
    private AnimeService animeService;

    private ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(animeService);
    }

    @Test
    public void saveImageFileSeparately() throws Exception {
        //given
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("poster", "poster.txt", "text/plain", "This is poster".getBytes());
        Anime anime = new Anime();
        anime.setId(id);

        when(animeService.getAnimeById(anyLong())).thenReturn(anime);

        ArgumentCaptor<Anime> argumentCaptor = ArgumentCaptor.forClass(Anime.class);

        //when
        imageService.savePoster(id, file);

        //then
        verify(animeService, times(1)).updateAnime(argumentCaptor.capture());
        Anime saved = argumentCaptor.getValue();
        assertEquals(file.getBytes().length, saved.getPoster().length);
    }

    @Test
    public void saveImageFileWithAnime() throws Exception {
        //given
        MultipartFile file = new MockMultipartFile("poster", "poster.txt", "text/plain", "This is poster".getBytes());
        Anime anime = new Anime();

        //when
        imageService.addPosterToAnime(anime, file);

        //then
        assertEquals(file.getBytes().length, anime.getPoster().length);
    }

}