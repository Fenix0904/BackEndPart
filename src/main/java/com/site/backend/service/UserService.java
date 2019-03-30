package com.site.backend.service;

import com.site.backend.domain.User;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    Iterable<User> getAllUsers();

    void deleteUser(Long userId);

    void updateUser(User updatedUser);
}
