package com.site.backend.service;

import com.site.backend.domain.User;
import com.site.backend.utils.exceptions.UserAlreadyExistException;
import com.site.backend.utils.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User createUser(User user) throws UserAlreadyExistException;

    Iterable<User> getAllUsers();

    User getUserById(Long userId) throws UserNotFoundException;

    User updateUser(UserDetails currentUser, User updatedUser);

    void deleteUser(Long userId);

    boolean activateUser(String code);
}
