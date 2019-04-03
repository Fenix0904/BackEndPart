package com.site.backend.controller;

import com.site.backend.domain.User;
import com.site.backend.service.UserService;
import com.site.backend.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/sign-up")
    public ResponseEntity registerUser(@RequestBody User user, BindingResult bindingResult) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getCode()); // TODO create Error entity
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }
        userService.createUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User updatedUser) {
        userService.updateUser(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
