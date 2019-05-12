package com.site.backend.validator;

import com.site.backend.domain.Anime;
import com.site.backend.utils.ResponseError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AnimeCreationValidator {

    public void validate(Anime anime, List<ResponseError> errors) {
        if (StringUtils.isEmpty(anime.getTitle())) {
            errors.add(new ResponseError("title", "Title cannot be empty!"));
        }
    }
}
