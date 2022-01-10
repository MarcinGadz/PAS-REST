package com.pas.app.DAO;

import com.pas.app.model.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Repository
public class TicketRepository extends RepositoryGeneric<Ticket> {
    public TicketRepository() {
        Seat seat = new Seat(2, 5, Hall.B);
        User user = new User("firstname", "lastname", "login");
//        Film film = new Film(
//                "Title",
//                "Genre",
//                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
//                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
//                BigDecimal.valueOf(100)
//        );
        Film film = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        seat.setId(UUID.fromString("7fd99a8e-6b42-11ec-90d6-0242ac120003"));
        user.setId(UUID.fromString("a20033ac-6b42-11ec-90d6-0242ac120003"));
        user.setActive(true);
        film.setId(UUID.fromString("9afaf470-6b42-11ec-90d6-0242ac120003"));

        Ticket ticket = new Ticket(user, film, seat);
        ticket.setId(UUID.fromString("22b24cd6-6b45-11ec-90d6-0242ac120003"));
        add(ticket);
    }
}
