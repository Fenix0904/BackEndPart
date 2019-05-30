package com.site.backend.utils;

import com.site.backend.domain.AnimeSeason;
import com.site.backend.domain.AnimeType;
import com.site.backend.domain.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AnimeSearchFilter {

    private Set<AnimeSeason> animeSeasons;
    private Set<AnimeType> types;
    private Set<Genre> genres;

    public boolean hesAnyParameters() {
        return animeSeasons != null && animeSeasons.size() > 0
                || types != null && types.size() > 0
                || genres != null && genres.size() > 0;
    }
}
