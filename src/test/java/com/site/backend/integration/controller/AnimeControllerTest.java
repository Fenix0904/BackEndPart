package com.site.backend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.backend.controller.AnimeController;
import com.site.backend.domain.*;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.service.AnimeService;
import com.site.backend.validator.AnimeCreationValidator;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
@ActiveProfiles({"local-storage", "work"})
public class AnimeControllerTest {
    @Autowired
    private AnimeService animeService;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeCreationValidator validator;

    private AnimeController controller;


    @Before
    public void setUp() {
        controller = new AnimeController(animeService, validator);

        AnimeSeason spring2019 = new AnimeSeason();
        spring2019.setSeason(Season.SPRING);
        spring2019.setYear(2019);
        spring2019.setId(1L);

        AnimeSeason winter2020 = new AnimeSeason();
        spring2019.setSeason(Season.WINTER);
        spring2019.setYear(2020);
        spring2019.setId(2L);

        Genre comedy = new Genre();
        comedy.setId(1L);
        comedy.setGenre("Comedy");

        Genre isekai = new Genre();
        isekai.setId(2L);
        isekai.setGenre("Isekai");

        seasonRepository.save(spring2019);
        seasonRepository.save(winter2020);
        genreRepository.save(comedy);
        genreRepository.save(isekai);
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

    @Test
    public void testCreateNewAnimeWithAlreadyStoredAndBrandNewProperties() throws Exception {
        // Already exists in the DB
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

        // Brand new one
        Genre romance = new Genre();
        romance.setGenre("Romance");

        Set<Genre> genres = new HashSet<>();
        genres.add(comedy);
        genres.add(isekai);
        genres.add(romance);

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


    /**
     * When deleting anime, its parameters (genre and season) must remain it he DB.
     */
    @Test
    public void testAnimeDeleting() throws Exception {
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

        Anime created = animeService.createNewAnime(swordArtOnline, null);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(
                delete("/animes/delete/" + created.getId())
        );

        assertNotNull(genreRepository.findByGenre(isekai.getGenre()));
        assertNotNull(genreRepository.findByGenre(comedy.getGenre()));
        assertFalse(animeRepository.findById(created.getId()).isPresent());
    }


    @Test
    @Transactional
    public void testAnimeUpdating() throws Exception {
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

        Anime created = animeService.createNewAnime(swordArtOnline, null);


        AnimeSeason winter2020 = new AnimeSeason();
        winter2020.setSeason(Season.WINTER);
        winter2020.setYear(2020);
        winter2020.setId(2L);
        Genre romance = new Genre();
        romance.setGenre("Romance");
        genres.clear();
        genres.add(romance);

        created.setTitle("Updated Title");
        created.setGenres(genres);
        created.setEpisodesCount(6);
        created.setAnimeSeason(winter2020);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String createdJSON = new ObjectMapper().writeValueAsString(created);
        mockMvc.perform(
                put("/animes/update/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdJSON)
        );

        Optional<Anime> updated = animeRepository.findById(created.getId());

        assertTrue(updated.isPresent());

        assertEquals(created.getGenres().size(), updated.get().getGenres().size());
        assertEquals(created.getTitle(), updated.get().getTitle());
        assertEquals(created.getEpisodesCount(), updated.get().getEpisodesCount());
        for (Genre genre : updated.get().getGenres()) {
            assertNotNull(genre.getId());
        }
    }
}