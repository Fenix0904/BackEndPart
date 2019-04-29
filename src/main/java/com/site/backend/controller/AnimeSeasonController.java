package com.site.backend.controller;

import com.site.backend.domain.AnimeSeason;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.utils.comparators.AnimeSeasonComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("animes")
public class AnimeSeasonController {

    private final SeasonRepository seasonRepository;

    @Autowired
    public AnimeSeasonController(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @GetMapping("/seasons")
    public Set<AnimeSeason> getAllAvailableSeasons() {
        List<AnimeSeason> all = (ArrayList<AnimeSeason>) seasonRepository.findAll();
        all.sort(new AnimeSeasonComparator());
        return new LinkedHashSet<>(all);
    }

}
