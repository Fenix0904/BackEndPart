package com.site.backend.repository;

import com.site.backend.domain.Anime;
import com.site.backend.repository.eagerFetching.AnimeEagerFetchingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long>, AnimeEagerFetchingRepository {

}
