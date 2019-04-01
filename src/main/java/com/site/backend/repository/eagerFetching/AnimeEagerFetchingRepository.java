package com.site.backend.repository.eagerFetching;

import com.site.backend.domain.Anime;

import java.util.Set;

public interface AnimeEagerFetchingRepository {
    Set<Anime> getAllAnimes();

    Anime getAnime(Long id);
}
