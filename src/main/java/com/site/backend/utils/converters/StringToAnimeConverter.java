package com.site.backend.utils.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.backend.domain.Anime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringToAnimeConverter implements Converter<String, Anime> {

    @Override
    public Anime convert(String s) {
        try {
            return new ObjectMapper().readValue(s, Anime.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
