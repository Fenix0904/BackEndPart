package com.site.backend.unit.service;

import com.site.backend.domain.Anime;
import com.site.backend.domain.AnimeType;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.GenreRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.repository.UserRepository;
import com.site.backend.service.AnimeService;
import com.site.backend.service.AnimeServiceImpl;
import com.site.backend.service.ImageService;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import com.site.backend.utils.exceptions.PosterException;
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
    private UserRepository userRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        animeService = new AnimeServiceImpl(animeRepository, genreRepository, seasonRepository, imageService, userRepository);
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
    public void whenSavingNewAnimeThenReturnIt() throws PosterException {
        Anime anime = new Anime();
        anime.setTitle("Test");
        anime.setType(AnimeType.TV);

        when(animeRepository.save(anime)).thenReturn(anime);

        Anime created = animeService.createNewAnime(anime, null);

        assertEquals(anime.getTitle(), created.getTitle());
        assertEquals(anime.getType(), created.getType());
    }

    @Test
    public void whenUpdatingAnimeThenUpdateOnlyRequestedParameters() throws PosterException, AnimeNotFoundException {
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
        when(animeRepository.findById(anyLong())).then((s) -> {
            Long id = s.getArgument(0);
            return Optional.of(data.get(id)) ;
        });

        Anime created = animeService.createNewAnime(anime, null);

        Anime newOne = new Anime();
        newOne.setId(created.getId());
        newOne.setTitle("Updated");

        animeService.updateAnime(newOne, null);

        Anime updated = animeService.getAnimeById(newOne.getId());

        assertEquals(1, data.size());
        assertNotEquals(anime.getTitle(), updated.getTitle());
        assertEquals(anime.getType(), updated.getType());
    }

    @Test
    public void testDeleteAnimeById() {
        //given
        Anime anime = new Anime();
        anime.setId(1L);
        Optional<Anime> optionalAnime = Optional.of(anime);

        when(animeRepository.findById(anyLong())).thenReturn(optionalAnime);

        //when
        animeService.deleteAnimeById(1L);

        //then
        verify(animeRepository, times(1)).delete(any());
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

    @Test
    public void testAnimeFiltering() {
        AnimeType type = AnimeType.TV;
    }
}