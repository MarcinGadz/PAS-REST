package com.pas.app.DAO;

import com.pas.app.model.Film;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Repository
public class FilmRepository extends RepositoryGeneric<Film> {
    public FilmRepository() {
//        addFilms();
    }

    private void addFilms() {
        Film film = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        film.setId(UUID.fromString("22bf50c7-bd22-4a80-ba53-becd2aa001b0"));
        this.add(film);
    }
}
