package com.site.backend.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorsCollector {

    public static List<ResponseError> collectErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(error ->
                        new ResponseError(
                                ((FieldError) error).getField(),
                                error.getCode()
                        ))
                .collect(Collectors.toList());
    }
}
