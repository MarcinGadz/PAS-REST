package com.pas.app.DAO;

import com.pas.app.model.Film;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Date;

@ApplicationScoped
public class FilmRepository extends RepositoryGeneric<Film> {
    FilmRepository() {
        addFilms();
    }

    private void addFilms() {
        Film film = new Film("Title", "Genre", null, null, 100);
        this.add(film);
    }
}
