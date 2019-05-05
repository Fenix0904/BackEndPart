create table anime
(
    id              bigserial not null,
    description     text,
    episodes_count  int4,
    poster          varchar(255),
    title           varchar(255),
    type            int4,
    anime_season_id int8,
    primary key (id)
);

create table usr
(
    id              bigserial    not null,
    activation_code varchar(255),
    email           varchar(255),
    is_active       boolean      not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

create table genre
(
    id    bigserial not null,
    genre varchar(255),
    primary key (id)
);

create table anime_genre
(
    anime_id int8 not null,
    genre_id int8 not null,
    primary key (anime_id, genre_id)
);

create table anime_staff
(
    anime_id int8 not null,
    user_id  int8 not null,
    primary key (anime_id, user_id)
);

create table anime_season
(
    id     bigserial not null,
    season int4,
    year   int4,
    primary key (id)
);


create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

alter table if exists anime
    add constraint anime_anime_season_fk
        foreign key (anime_season_id) references anime_season;
alter table if exists anime_genre
    add constraint anime_genre_genre_fk
        foreign key (genre_id) references genre;
alter table if exists anime_genre
    add constraint anime_genre_anime_fk
        foreign key (anime_id) references anime;
alter table if exists anime_staff
    add constraint anime_staff_user_fk
        foreign key (user_id) references usr;
alter table if exists anime_staff
    add constraint anime_staff_anime_fk
        foreign key (anime_id) references anime;
alter table if exists user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr;
