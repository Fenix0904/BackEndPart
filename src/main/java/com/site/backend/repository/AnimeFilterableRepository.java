package com.site.backend.repository;

import com.site.backend.domain.Anime;
import com.site.backend.utils.AnimeSearchFilter;

import java.util.Set;

public interface AnimeFilterableRepository {
    Set<Anime> getAnimeByFilterParameters(AnimeSearchFilter filter);
}
