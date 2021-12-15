package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.model.Film;
import com.pas.app.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (f != null) {
            return f.getTickets();
        }
        return new ArrayList<>();
    }


    @Override
    public synchronized void remove(Film object) {
        object = getById(object.getId());
        if (object == null || object.getTickets() == null
                || object.getTickets().isEmpty()
                || object.getEndTime().isBefore(LocalDateTime.now())) {
            super.remove(object);
        } else {
            throw new IllegalStateException("Cannot remove film");
        }
    }
}
