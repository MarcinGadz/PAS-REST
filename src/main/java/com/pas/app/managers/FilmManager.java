package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Film;
import com.pas.app.model.Ticket;

import javax.inject.Inject;
import java.time.Instant;
import java.util.*;

public class FilmManager extends ManagerGeneric<Film> {
    @Inject
    public FilmManager(FilmRepository repo) {
        super(repo);
    }

    public List<Ticket> getTickets(UUID id) {
        Film f = getById(id);
        if(f != null) {
            return f.getTickets();
        }
        return new ArrayList<>();
    }


//    @Override
//    public void remove(Film object) {
//        if(object.getTickets().isEmpty() || object.getEndTime().before(Date.from(Instant.now()))) {
//            super.remove(object);
//        }
//    }
}
