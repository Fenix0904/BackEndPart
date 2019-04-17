package com.site.backend.repository;

import com.site.backend.domain.Season;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends CrudRepository<Season, Long> {
}
