create table public.movies
(
    id           bigserial
        primary key,
    title        varchar(400),
    release_date date,
    tagline      varchar(500),
    description  varchar(5000),
    budget       integer,
    status       varchar(100),
    revenue      double precision,
    poster_url   varchar(1000)
);
create table public.genres
(
    id   serial
        primary key,
    name varchar(300)
        unique
);
create table public.movies_to_genres
(
    movie_id bigint  not null
        references public.movies,
    genre_id integer not null
        references public.genres,
    primary key (movie_id, genre_id)
);
