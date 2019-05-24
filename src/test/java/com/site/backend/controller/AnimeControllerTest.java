package com.site.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.backend.domain.Anime;
import com.site.backend.service.AnimeService;
import com.site.backend.service.ImageService;
import com.site.backend.validator.AnimeCreationValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnimeControllerTest {
    @Mock
    private AnimeService animeService;
    @Mock
    private ImageService imageService;

    private Anime anime;
    @Mock
    private AnimeCreationValidator validator;

    private AnimeController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new AnimeController(animeService, imageService, validator);
    }

    @Test
    public void whenGetAllAnimesThenExpectStatusOKAndListOfAnimes() throws Exception {
        anime = new Anime();
        ArrayList<Anime> animes = new ArrayList<>();
        animes.add(anime);

        when(animeService.getAll()).thenReturn(animes);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/animes/"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writer().writeValueAsString(animes)));
    }

    @Test
    public void whenGetAnimeByIdThenExpectStatusOkAndAnime() throws Exception {
        anime = new Anime();

        when(animeService.getAnimeById(anyLong())).thenReturn(anime);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/animes/" + anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writer().writeValueAsString(anime)));
    }
}