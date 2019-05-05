package com.site.backend.bootstarter;

import com.site.backend.domain.*;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.SeasonRepository;
import com.site.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class EntityCreator implements ApplicationListener<ContextRefreshedEvent> {

    private final SeasonRepository seasonRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EntityCreator(AnimeRepository animeRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, SeasonRepository seasonRepository) {
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        fillDatabaseWithData();
    }

    private void fillDatabaseWithData() {

        AnimeSeason spring2019 = new AnimeSeason();
        spring2019.setSeason(Season.SPRING);
        spring2019.setYear(2019);

        AnimeSeason spring2018 = new AnimeSeason();
        spring2018.setSeason(Season.SPRING);
        spring2018.setYear(2018);

        AnimeSeason winter2019 = new AnimeSeason();
        winter2019.setSeason(Season.WINTER);
        winter2019.setYear(2019);

        AnimeSeason winter2018 = new AnimeSeason();
        winter2018.setSeason(Season.WINTER);
        winter2018.setYear(2018);

        AnimeSeason autumn2019 = new AnimeSeason();
        autumn2019.setSeason(Season.AUTUMN);
        autumn2019.setYear(2019);

        AnimeSeason autumn2018 = new AnimeSeason();
        autumn2018.setSeason(Season.AUTUMN);
        autumn2018.setYear(2018);

        AnimeSeason summer2019 = new AnimeSeason();
        summer2019.setSeason(Season.SUMMER);
        summer2019.setYear(2019);

        AnimeSeason summer2018 = new AnimeSeason();
        summer2018.setSeason(Season.SUMMER);
        summer2018.setYear(2018);

        Genre comedy = new Genre();
        comedy.setGenre("Comedy");

        Genre romance = new Genre();
        romance.setGenre("Romance");

        Genre action = new Genre();
        action.setGenre("Action");

        Genre fantasy = new Genre();
        fantasy.setGenre("Fantasy");

        Genre magic = new Genre();
        magic.setGenre("Magic");

        Genre isekai = new Genre();
        isekai.setGenre("Isekai");

        User admin = new User();
        admin.setUsername("admin");
        admin.setActive(true);
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRoles(Collections.singleton(Role.ADMIN));

        User user = new User();
        user.setActive(true);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singleton(Role.USER));

        Set<Genre> konosubaGenres = new HashSet<>();
        konosubaGenres.add(comedy);
        konosubaGenres.add(isekai);
        konosubaGenres.add(magic);

        Set<Genre> saoGenres = new HashSet<>();
        saoGenres.add(fantasy);
        saoGenres.add(action);
        saoGenres.add(romance);

        Anime swordArtOnline = new Anime();
        swordArtOnline.setTitle("Sword Art Online");
        swordArtOnline.setAnimeSeason(spring2018);
        swordArtOnline.setDescription("Some very loooooooooooong text with anime description. Yeah, it can be quite long.");
        swordArtOnline.setType(AnimeType.TV);
        swordArtOnline.setEpisodesCount(24);
        swordArtOnline.setGenres(konosubaGenres);

        Anime konosuba =  new Anime();
        konosuba.setTitle("Konosuba");
        konosuba.setAnimeSeason(winter2018);
        konosuba.setDescription("Some very loooooooooooong text with anime description. Yeah, it can be quite long.");
        konosuba.setType(AnimeType.ONA);
        konosuba.setEpisodesCount(12);
        konosuba.setGenres(saoGenres);

        userRepository.save(admin);
        userRepository.save(user);

        animeRepository.save(swordArtOnline);
        animeRepository.save(konosuba);

        seasonRepository.save(spring2018);
        seasonRepository.save(spring2019);
        seasonRepository.save(winter2018);
        seasonRepository.save(winter2019);
        seasonRepository.save(autumn2018);
        seasonRepository.save(autumn2019);
        seasonRepository.save(summer2018);
        seasonRepository.save(summer2019);
    }
}
