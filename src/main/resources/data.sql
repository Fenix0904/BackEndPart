INSERT INTO usr (id, activation_code, email, is_active, password, username) VALUES (1, null, null, true, '$2a$10$ixc1mwP6.pR/.j6DBA/an.BESF5H9Yl6.qtbJ2YwvsnXxekwmArma', 'admin');
INSERT INTO usr (id, activation_code, email, is_active, password, username) VALUES (2, null, null, true, '$2a$10$iusUrpbjYmKSGgwzGwlxf.aKJU6DKnyaucrkXlqo4NXKAMpsRCIL.', 'user');

INSERT INTO user_role (user_id, roles) VALUES (1, 'ADMIN');
INSERT INTO user_role (user_id, roles) VALUES (2, 'USER');

INSERT INTO anime_season (id, season, year) VALUES (1, 1, 2018);
INSERT INTO anime_season (id, season, year) VALUES (2, 0, 2018);
INSERT INTO anime_season (id, season, year) VALUES (3, 1, 2019);
INSERT INTO anime_season (id, season, year) VALUES (4, 0, 2019);
INSERT INTO anime_season (id, season, year) VALUES (5, 3, 2018);
INSERT INTO anime_season (id, season, year) VALUES (6, 3, 2019);
INSERT INTO anime_season (id, season, year) VALUES (7, 2, 2018);
INSERT INTO anime_season (id, season, year) VALUES (8, 2, 2019);

INSERT INTO genre (id, genre) VALUES (1, 'Isekai');
INSERT INTO genre (id, genre) VALUES (2, 'Magic');
INSERT INTO genre (id, genre) VALUES (3, 'Comedy');
INSERT INTO genre (id, genre) VALUES (4, 'Fantasy');
INSERT INTO genre (id, genre) VALUES (5, 'Romance');
INSERT INTO genre (id, genre) VALUES (6, 'Action');

INSERT INTO anime (id, description, episodes_count, poster, title, type, anime_season_id) VALUES (66666, 'Some very loooooooooooong text with anime description. Yeah, it can be quite long.', 24, null, 'Sword Art Online', 0, 1);
INSERT INTO anime (id, description, episodes_count, poster, title, type, anime_season_id) VALUES (99999, 'Some very loooooooooooong text with anime description. Yeah, it can be quite long.', 12, null, 'Konosuba', 2, 2);

INSERT INTO anime_genre (anime_id, genre_id) VALUES (66666, 1);
INSERT INTO anime_genre (anime_id, genre_id) VALUES (66666, 2);
INSERT INTO anime_genre (anime_id, genre_id) VALUES (66666, 3);
INSERT INTO anime_genre (anime_id, genre_id) VALUES (99999, 4);
INSERT INTO anime_genre (anime_id, genre_id) VALUES (99999, 5);
INSERT INTO anime_genre (anime_id, genre_id) VALUES (99999, 6);




