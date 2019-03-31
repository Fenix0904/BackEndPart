package com.site.backend.repository;

import com.site.backend.domain.Anime;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class AnimeEagerFetchingRepositoryImpl implements AnimeEagerFetchingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List getAllEntities() {
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        List<Anime> resultList = (List<Anime>) session.createQuery("from Anime").getResultList();
        for (Anime anime : resultList) {
            Hibernate.initialize(anime.getGenres());
        }
        return resultList;
    }
}
