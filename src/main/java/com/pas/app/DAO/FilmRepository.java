package com.pas.app.DAO;

import com.pas.app.model.Film;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class FilmRepository extends RepositoryGeneric<Film> {
    public FilmRepository() {
        addFilms();
    }

    private void addFilms() {
        Film film = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        film.setId(UUID.fromString("6bf41b16-5c60-11ec-bf63-0242ac130002"));
        this.add(film);
    }
}
