package com.site.backend.service;

import com.site.backend.domain.User;
import com.site.backend.utils.exceptions.UserAlreadyExistException;
import com.site.backend.utils.exceptions.UserNotFoundException;

public interface UserService {

    User createUser(User user) throws UserAlreadyExistException;

    User getUserById(Long userId) throws UserNotFoundException;

    Iterable<User> getAllUsers();

    void deleteUser(Long userId);

    void updateUser(User updatedUser);

    boolean activateUser(String code);
}
