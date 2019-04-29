package com.site.backend.repository;

import com.site.backend.domain.AnimeSeason;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends CrudRepository<AnimeSeason, Long> {
}
