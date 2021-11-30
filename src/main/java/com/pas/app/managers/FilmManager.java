package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Film;

public class FilmManager extends ManagerGeneric<Film> {
    public FilmManager(RepositoryGeneric<Film> repo) {
        super(repo);
    }
}
