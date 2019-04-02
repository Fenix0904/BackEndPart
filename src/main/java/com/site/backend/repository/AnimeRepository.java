package com.site.backend.repository;

import com.site.backend.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;

//Session session = entityManager.unwrap(org.hibernate.Session.class);

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    @Query("from Anime a left join fetch a.genres left join fetch a.staff")
    Set<Anime> getAllEagerly();

    @Query("from Anime a left join fetch a.genres left join fetch a.staff where a.id = :id")
    Anime getByIdEagerly(@Param("id") Long id);
}
