package com.site.backend.bootstarter;

import com.site.backend.domain.Anime;
import com.site.backend.domain.Genre;
import com.site.backend.domain.Role;
import com.site.backend.domain.User;
import com.site.backend.repository.AnimeRepository;
import com.site.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityCreator implements ApplicationListener<ContextRefreshedEvent> {

    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    @Autowired
    public EntityCreator(AnimeRepository animeRepository, UserRepository userRepository) {
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        fillDatabaseWithData();
    }

    private void fillDatabaseWithData() {

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
        admin.setUsername("Admin");
        admin.setPassword("password");
        admin.setRole(Role.ADMIN);

        User user = new User();
        user.setUsername("User");
        user.setPassword("password");
        user.setRole(Role.USER);

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
        swordArtOnline.setYear(Calendar.getInstance().getTime());
        swordArtOnline.setDescription("Some very loooooooooooong text with anime description. Yeah, it can be quite long.");
        swordArtOnline.setType("24 ep");
        swordArtOnline.setGenres(konosubaGenres);

        Anime konosuba =  new Anime();
        konosuba.setTitle("Konosuba");
        konosuba.setYear(Calendar.getInstance().getTime());
        konosuba.setDescription("Some very loooooooooooong text with anime description. Yeah, it can be quite long.");
        konosuba.setType("12 ep");
        konosuba.setGenres(saoGenres);

        userRepository.save(admin);
        userRepository.save(user);

        animeRepository.save(swordArtOnline);
        animeRepository.save(konosuba);
    }
}
