package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.domain.AnimeSeason;
import com.site.backend.domain.AnimeType;
import com.site.backend.domain.Season;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.utils.exceptions.PosterException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnimeServiceIntegrationTest {

    private AnimeService animeService;

    @Autowired
    private AnimeRepository animeRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        animeService = new AnimeServiceImpl(animeRepository, genreRepository, seasonRepository, imageService);
    }

    @Test
    public void updateAnime() throws PosterException {
        AnimeSeason season = new AnimeSeason();
        season.setYear(2019);
        season.setSeason(Season.SPRING);
        Anime anime = new Anime();
        anime.setTitle("Test");
        anime.setType(AnimeType.TV);
        anime.setAnimeSeason(season);

        Anime created = animeRepository.save(anime);

        AnimeSeason createdSeason = created.getAnimeSeason();
        createdSeason.setYear(2018);

        created.setTitle("Updated");
        created.setAnimeSeason(createdSeason);

        Anime updated = animeService.createNewAnime(created, null);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Updated", updated.getTitle());
        assertEquals(updated.getAnimeSeason(), createdSeason);
    }

}
