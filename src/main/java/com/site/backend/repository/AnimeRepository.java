package com.site.backend.repository;

import com.site.backend.domain.Anime;
import org.springframework.data.repository.CrudRepository;

public interface AnimeRepository extends CrudRepository<Anime, Long> {

}
