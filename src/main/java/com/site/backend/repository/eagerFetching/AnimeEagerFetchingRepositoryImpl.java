package com.site.backend.repository.eagerFetching;

import com.site.backend.domain.Anime;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;

@Repository
public class AnimeEagerFetchingRepositoryImpl implements AnimeEagerFetchingRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @SuppressWarnings("unchecked")
    public Set<Anime> getAllAnimes() {
        //Session session = entityManager.unwrap(org.hibernate.Session.class);
        List<Anime> resultList = (List<Anime>) entityManager.createQuery("from Anime a left join fetch a.genres left join fetch a.staff").getResultList();
        return new LinkedHashSet<>(resultList);
    }

    @Override
    public Anime getAnime(Long id) {
        return (Anime) entityManager.createQuery("from Anime a left join fetch a.genres left join fetch a.staff where a.id = " + id).getSingleResult();
    }
}
