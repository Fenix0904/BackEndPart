package com.site.backend.validator;

import com.site.backend.domain.User;
import com.site.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User receivedUser = (User) o;
        if (receivedUser.getUsername() == null) {
            errors.rejectValue("username", "Username cannot be empty!");
        } else {
            if (userRepository.existsUserByUsername(receivedUser.getUsername())) {
                errors.rejectValue("username", "User with this username is already registered!");
            }
        }
        if (receivedUser.getPasswordRepeat() == null) {
            errors.rejectValue("passwordRepeat", "Password repeat cannot be empty!");
        } else {
            if (!receivedUser.getPassword().equals(receivedUser.getPasswordRepeat())) {
                errors.rejectValue("passwordRepeat", "Password mismatch!");
            }
        }
        if (receivedUser.getPassword() == null) {
            errors.rejectValue("password", "Password cannot be empty!");
        } else {
            if (receivedUser.getPassword().length() < 6) {
                errors.rejectValue("password", "The password should be at least 6 symbols.");
            }
        }
    }
}
