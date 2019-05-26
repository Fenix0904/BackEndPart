package com.site.backend.utils.exceptions.handler;

import com.site.backend.utils.ResponseError;
import com.site.backend.utils.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AnimeNotFoundException.class, ContentNotAllowedException.class, UserNotFoundException.class, PosterException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        // TODO: add logging
        ResponseError error = new ResponseError();
        if (ex instanceof AnimeNotFoundException) {
            error.setErrorMessage("There are no anime with such an id!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } else if (ex instanceof UserNotFoundException) {
            error.setErrorMessage("There are no user with such an id!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } else if (ex instanceof ContentNotAllowedException) {
            error.setErrorMessage("Something is wrong with your request!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else if (ex instanceof UserAlreadyExistException) {
            error.setField("username");
            error.setErrorMessage("User with such username is already registered!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } else if (ex instanceof PosterException) {
            error.setField("poster");
            error.setErrorMessage("Something went wrong during poster saving...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } else {
            error.setErrorMessage("Something went wrong...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
