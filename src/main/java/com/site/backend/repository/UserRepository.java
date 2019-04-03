package com.site.backend.repository;

import com.site.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    boolean existsUserByUsername(String username);
}
