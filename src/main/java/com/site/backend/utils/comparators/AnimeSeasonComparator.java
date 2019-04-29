package com.site.backend.utils.comparators;

import com.site.backend.domain.AnimeSeason;

import java.util.Comparator;

public class AnimeSeasonComparator implements Comparator<AnimeSeason> {

    @Override
    public int compare(AnimeSeason o1, AnimeSeason o2) {
        int firstYear = o1.getYear();
        int secondYear = o2.getYear();
        if (firstYear > secondYear) {
            return -1;
        } else if (firstYear < secondYear) {
            return 1;
        } else {
            return o1.getSeason().compareTo(o2.getSeason());
        }
    }
}
