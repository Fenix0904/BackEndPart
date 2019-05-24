package com.site.backend.repository;

import com.site.backend.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

//Session session = entityManager.unwrap(org.hibernate.Session.class);

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
