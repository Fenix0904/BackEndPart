package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.domain.AnimeType;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AnimeServiceTest {

    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private SeasonRepository seasonRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        animeService = new AnimeServiceImpl(animeRepository, genreRepository, seasonRepository);
    }

    @Test
    public void whenLoadByIdThenReturnEntity() throws AnimeNotFoundException {
        Anime anime = new Anime();
        Optional<Anime> optionalAnime = Optional.of(anime);

        when(animeRepository.findById(anyLong())).thenReturn(optionalAnime);
        Anime returnedValue = animeService.getAnimeById(1L);

        assertNotEquals(returnedValue, null);
        verify(animeRepository, times(1)).findById(anyLong());
        verify(animeRepository, never()).findAll();
    }

    @Test(expected = AnimeNotFoundException.class)
    public void whenLoadByInvalidIdThenThrowException() throws Exception {
        Optional<Anime> optionalAnime = Optional.empty();
        when(animeRepository.findById(anyLong())).thenReturn(optionalAnime);
        animeService.getAnimeById(-1L);
    }

    @Test
    public void whenSavingNewAnimeThenReturnIt() {
        Anime anime = new Anime();
        anime.setTitle("Test");
        anime.setType(AnimeType.TV);

        when(animeRepository.save(anime)).thenReturn(anime);

        Anime created = animeService.createNewAnime(anime);

        assertEquals(anime.getTitle(), created.getTitle());
        assertEquals(anime.getType(), created.getType());
    }

    @Test
    public void whenUpdatingAnimeThenReturnUpdatedOne() {

        Map<Long, Anime> data = new HashMap<>();

        Anime anime = new Anime();
        anime.setId(1L);
        anime.setTitle("Test");
        anime.setType(AnimeType.TV);

        when(animeRepository.save(any())).then((s) -> {
            Anime a = s.getArgument(0);
            Anime tmp = new Anime();

            tmp.setId(a.getId());
            tmp.setTitle(a.getTitle());
            tmp.setType(a.getType());

            data.put(a.getId(), tmp);
            return data.get(a.getId());
        });

        Anime created = animeService.createNewAnime(anime);

        created.setTitle("Updated");

        animeService.updateAnime(created);

        assertEquals(1, data.size());
        assertNotEquals(anime.getTitle(), created.getTitle());
    }


    @Test
    public void testDeleteAnimeById() {
        animeService.deleteAnimeById(1L);

        verify(animeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetAllAnimes() {
        Anime anime = new Anime();
        ArrayList<Anime> animes = new ArrayList<>();
        animes.add(anime);

        when(animeRepository.findAll()).thenReturn(animes);

        List<Anime> all = (List<Anime>) animeService.getAll();

        assertEquals(animes.size(), all.size());
        verify(animeRepository, times(1)).findAll();
    }
}