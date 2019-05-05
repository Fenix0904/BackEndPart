package com.site.backend.validator;

import com.site.backend.domain.Anime;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AnimeCreationValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Anime.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Anime anime = (Anime) o;
        if (StringUtils.isEmpty(anime.getTitle())) {
            errors.rejectValue("title", "Title cannot be empty!");
        }
    }
}
