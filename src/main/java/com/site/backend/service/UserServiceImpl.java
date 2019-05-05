package com.site.backend.service;

import com.site.backend.domain.Role;
import com.site.backend.domain.User;
import com.site.backend.repository.UserRepository;
import com.site.backend.utils.exceptions.UserAlreadyExistException;
import com.site.backend.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailSender mailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(Role.USER));
        }
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(false);
        sendMessage(user);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(UserDetails userDetails, User updatedUser) {
        boolean userIsAdmin = userDetails.getAuthorities().contains(Role.ADMIN);

        if (userDetails.getUsername().equals(updatedUser.getUsername())
                || userIsAdmin) {

            User currentUser = userRepository.findByUsername(updatedUser.getUsername());

            // checking id
            if (updatedUser.getId() == null) {
                updatedUser.setId(currentUser.getId());
            }

            // checking password
            if (!bCryptPasswordEncoder.matches(updatedUser.getPassword(), currentUser.getPassword())) {
                updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            }

            // checking Email
            String currentUserEmail = currentUser.getEmail();
            String newUserEmail = updatedUser.getEmail();
            boolean eMailWasChanged = (newUserEmail != null && !newUserEmail.equals(currentUserEmail))
                    || (currentUserEmail != null && !currentUserEmail.equals(newUserEmail));
            if (eMailWasChanged) {
                updatedUser.setEmail(newUserEmail);
                if (!StringUtils.isEmpty(newUserEmail)) {
                    updatedUser.setActive(false);
                    updatedUser.setActivationCode(UUID.randomUUID().toString());
                }
            }

            // checking roles (only admin can change it)
            if (userIsAdmin) {
                Set<Role> newRoles = updatedUser.getRoles();
                if (newRoles != null) {
                    Set<Role> currentRoles = currentUser.getRoles();
                    currentRoles.addAll(newRoles);
                    updatedUser.setRoles(currentRoles);
                }
            }

            User savedUser = userRepository.save(updatedUser);

            if (eMailWasChanged) {
                sendMessage(updatedUser);
            }

            return savedUser;
        }

        return null;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! <br>" +
                    "Welcome to AniSite. Please, visit next link for activation " +
                    "http://localhost:8080/users/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation Code", message);
        }
    }
}
