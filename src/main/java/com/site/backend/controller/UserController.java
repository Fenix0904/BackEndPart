package com.site.backend.controller;

import com.site.backend.domain.User;
import com.site.backend.service.UserService;
import com.site.backend.utils.ErrorsCollector;
import com.site.backend.utils.ResponseError;
import com.site.backend.utils.exceptions.UserAlreadyExistException;
import com.site.backend.utils.exceptions.UserNotFoundException;
import com.site.backend.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRegistrationValidator validator;

    @Autowired
    public UserController(UserService userService, UserRegistrationValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity registerUser(@Valid @RequestBody User user, BindingResult bindingResult) throws UserAlreadyExistException {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ResponseError> errors = ErrorsCollector.collectErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }
        userService.createUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity activateUser(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            return new ResponseEntity(HttpStatus.OK); // user was activated.
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND); // user was already activated and {code} is not valid anymore.
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal UserDetails currentUser, @RequestBody User updatedUser) {
        User user = userService.updateUser(currentUser, updatedUser);
        if (user == null) {
            ResponseError error = new ResponseError();
            error.setErrorMessage("Looks like you have no rights to update this user.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id); // TODO
    }
}
