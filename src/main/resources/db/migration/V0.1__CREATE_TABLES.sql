create table if not exists cinema(
    id serial primary key,
    name text  not null,
    city text
);

create table if not exists cinema_hall(
    id serial primary key,
    number text not null,
    cinema_id integer references cinema(id)
);

create table if not exists seat(
    id serial primary key,
    row text not null,
    number text not null,
    cinema_hall_id integer references cinema_hall(id)
);

create table if not exists movie(
    id serial primary key,
    title text not null constraint must_have_unique_name unique
);

create table if not exists show(
    id serial primary key,
    movie_id integer references movie(id),
    cinema_hall_id integer references cinema_hall(id),
    start_time timestamp not null,
    end_time timestamp not null
);

create table if not exists booking(
    id serial primary key,
    show_id integer references show(id)
);

create table if not exists  tickets (
    id serial primary key,
    booking_id integer references booking(id),
    seat_id integer references seat(id),
    show_id integer references show(id)
);
