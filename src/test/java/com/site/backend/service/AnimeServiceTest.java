package com.site.backend.service;

import com.site.backend.domain.Anime;
import com.site.backend.repository.AnimeRepository;
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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        animeService = new AnimeServiceImpl(animeRepository);
    }

    @Test
    public void whenLoadByIdThenReturnEntity() {
        Anime anime = new Anime();

        when(animeRepository.getByIdEagerly(anyLong())).thenReturn(anime);
        Anime returnedValue = animeService.getAnimeById(1L);

        assertNotEquals(returnedValue, null);
        verify(animeRepository, times(1)).getByIdEagerly(anyLong());
    }
}