package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Film;
import com.pas.app.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.*;

@ApplicationScoped
public class FilmManager extends ManagerGeneric<Film> {

    private FilmRepository repository;

    public FilmManager() {

    }

    public FilmRepository getRepository() {
        return repository;
    }

    @Inject
    public void setRepository(FilmRepository repository) {
        this.repository = repository;
        super.setRepo(repository);
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
//        if(object.getTickets().isEmpty() || object.getEndTime().isBefore(Date.from(Instant.now()))) {
//            super.remove(object);
//        }
//    }
}
