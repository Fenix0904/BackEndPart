package com.site.backend.controller;

import com.site.backend.domain.User;
import com.site.backend.service.UserService;
import com.site.backend.utils.ResponseError;
import com.site.backend.utils.exceptions.UserNotFoundException;
import com.site.backend.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity registerUser(@RequestBody User user, BindingResult bindingResult) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<ResponseError> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(
                        new ResponseError(
                                ((FieldError) error).getField(),
                                error.getCode()
                        )
                );
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User updatedUser) {
        userService.updateUser(updatedUser); // TODO
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id); // TODO
    }
}
