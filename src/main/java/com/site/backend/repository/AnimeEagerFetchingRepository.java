package com.site.backend.repository;

import java.util.List;

public interface AnimeEagerFetchingRepository<T> {
    List<T> getAllEntities();
}
