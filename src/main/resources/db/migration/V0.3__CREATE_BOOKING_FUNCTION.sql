Create or replace function bookTheSeat(showID integer, Variadic arrSeatID integer[])

returns integer
as $$

declare
    seatID integer;
    bookingID integer;
    count integer;

 begin
 insert into booking (show_id) values
        (showID);
 bookingID = currval('booking_id_seq');

 for i in 1..array_length(arrSeatID, 1) loop
        seatID = arrSeatID[i];
        select count(*) into count from seat s
	        join cinema_hall ch on ch.id = s.cinema_hall_id
            join show sh on sh.cinema_hall_id = ch.id
			    and sh.id = showID
            where s.id = seatID;

        if count <> 1
        then raise exception 'The seat % not from this cinema hall', seatID;
        end if;

        insert into tickets (booking_id, seat_id, show_id) values
            (bookingID, seatID, showID);
        count := 0;
 end loop;
    return bookingID;

end; $$

language 'plpgsql';
