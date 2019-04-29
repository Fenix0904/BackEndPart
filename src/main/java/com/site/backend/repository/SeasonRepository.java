package com.site.backend.repository;

import com.site.backend.domain.AnimeSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<AnimeSeason, Long> {
}
