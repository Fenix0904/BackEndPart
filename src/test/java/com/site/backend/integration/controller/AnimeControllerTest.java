package com.site.backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.backend.domain.*;
import com.site.backend.controller.AnimeController;
import com.site.backend.service.AnimeService;
import com.site.backend.validator.AnimeCreationValidator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
public class AnimeControllerTest {
    @Autowired
    private AnimeService animeService;
    @Autowired
    private AnimeCreationValidator validator;

    private AnimeController controller;

    @Before
    public void setUp() {
        controller = new AnimeController(animeService, validator);
    }

    @Test
    public void testCreateNewAnimeWithBrandNewProperties() throws Exception {
        AnimeSeason spring2019 = new AnimeSeason();
        spring2019.setSeason(Season.SPRING);
        spring2019.setYear(2019);

        Genre comedy = new Genre();
        comedy.setGenre("Comedy");

        Genre isekai = new Genre();
        isekai.setGenre("Isekai");

        Set<Genre> genres = new HashSet<>();
        genres.add(comedy);
        genres.add(isekai);

        Anime swordArtOnline = new Anime();
        swordArtOnline.setTitle("Integration test");
        swordArtOnline.setAnimeSeason(spring2019);
        swordArtOnline.setDescription("This is integration test description");
        swordArtOnline.setType(AnimeType.TV);
        swordArtOnline.setEpisodesCount(12);
        swordArtOnline.setGenres(genres);

        String contentPOST = new ObjectMapper().writer().writeValueAsString(swordArtOnline);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(
                    post("/animes/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(contentPOST)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateNewAnimeWithAlreadyStoredProperties() throws Exception {
        AnimeSeason spring2019 = new AnimeSeason();
        spring2019.setSeason(Season.SPRING);
        spring2019.setYear(2019);
        spring2019.setId(1L);

        Genre comedy = new Genre();
        comedy.setId(1L);
        comedy.setGenre("Comedy");

        Genre isekai = new Genre();
        isekai.setId(2L);
        isekai.setGenre("Isekai");

        Set<Genre> genres = new HashSet<>();
        genres.add(comedy);
        genres.add(isekai);

        Anime swordArtOnline = new Anime();
        swordArtOnline.setTitle("Integration test");
        swordArtOnline.setAnimeSeason(spring2019);
        swordArtOnline.setDescription("This is integration test description");
        swordArtOnline.setType(AnimeType.TV);
        swordArtOnline.setEpisodesCount(12);
        swordArtOnline.setGenres(genres);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(
                post("/animes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writer().writeValueAsString(swordArtOnline))
        )
                .andExpect(status().isOk());
    }

}