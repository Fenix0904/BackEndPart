package com.site.backend.repository;

import com.site.backend.domain.*;
import com.site.backend.utils.AnimeSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CustomAnimeRepositoryImpl implements CustomAnimeRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomAnimeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Anime> getAnimeByFilterParameters(AnimeSearchFilter filter) {
        if (filter.hesAnyParameters()) {
            boolean wasFiltered = false;
            StringBuilder query = new StringBuilder();
            query.append("SELECT DISTINCT a FROM Anime a " +
                    " JOIN a.genres genre " +
                    "WHERE ");
            if (filter.getGenres() != null) {
                query.append("(genre.id IN (");
                for (Genre genre : filter.getGenres()) {
                    query.append(genre.getId()).append(",");
                }
                query.deleteCharAt(query.length() - 1);
                query.append(")) ");
                wasFiltered = true;
            }
            if (filter.getAnimeSeasons() != null) {
                if (wasFiltered) {
                    query.append("AND ");
                }
                query.append("(a.animeSeason.id IN (");
                for (AnimeSeason animeSeason : filter.getAnimeSeasons()) {
                    query.append(animeSeason.getId()).append(",");
                }
                query.deleteCharAt(query.length() - 1);
                query.append(")) ");
                wasFiltered = true;
            }
            if (filter.getTypes() != null) {
                if (wasFiltered) {
                    query.append("AND ");
                }
                query.append("(a.type IN (");
                for (AnimeType animeType : filter.getTypes()) {
                    query.append(animeType.ordinal()).append(",");
                }
                query.deleteCharAt(query.length() - 1);
                query.append(")) ");
            }
            query.append("ORDER BY a.animeSeason desc");

            List<Anime> resultList = entityManager.createQuery(query.toString(), Anime.class).getResultList();
            return new LinkedHashSet<>(resultList);
        }
        return null;
    }
}
