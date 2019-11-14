insert into cinema (name, city) values
    ('Cinema World','Novosibirsk');


insert into cinema_hall(number, cinema_id) values
    ('1', 1),
    ('2', 1);

DO
$$
begin
    for r in 1..8 loop
        for n in 1..10 loop
            insert into seat (row, number, cinema_hall_id) values
                (r, n, 1),
                (r, n, 2);
        end loop;
    end loop;
end;
$$;

insert into movie (title) values
    ('The Shawshank Redemption'),
    ('The Godfather');

insert into show (movie_id, cinema_hall_id, start_time, end_time) values
    (1, 1, now()::timestamp  + interval '2 day', now()::timestamp + interval '2 day' + interval '2 hour'),
    (1, 1, now()::timestamp  + interval '1 day' - interval '2 hour', now()::timestamp + interval '1 day');