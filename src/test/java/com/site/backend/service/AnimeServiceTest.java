package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.utils.exceptions.AnimeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AnimeServiceTest {

    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        animeService = new AnimeServiceImpl(animeRepository);
    }

    @Test
    public void whenLoadByIdThenReturnEntity() throws AnimeNotFoundException {
        Anime anime = new Anime();

        when(animeRepository.getByIdEagerly(anyLong())).thenReturn(anime);
        Anime returnedValue = animeService.getAnimeByIdEagerly(1L);

        assertNotEquals(returnedValue, null);
        verify(animeRepository, times(1)).getByIdEagerly(anyLong());
        verify(animeRepository, never()).findAll();
    }

    @Test(expected = AnimeNotFoundException.class)
    public void whenLoadByInvalidIdThenThrowException() throws Exception {
        when(animeRepository.getByIdEagerly(anyLong())).thenReturn(null);
        animeService.getAnimeById(-1L);
    }
}