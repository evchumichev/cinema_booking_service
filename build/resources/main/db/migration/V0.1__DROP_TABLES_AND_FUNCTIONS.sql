drop table if exists tickets, booking, show, movie, seat, cinema_hall, cinema cascade;


drop function if exists bookTheSeat(showID integer, Variadic arrSeatID integer[]);
