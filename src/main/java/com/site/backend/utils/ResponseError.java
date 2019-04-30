package com.site.backend.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseError {
    private Date timestamp = new Date();
    private String field;
    private String errorMessage;

    public ResponseError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }
}
