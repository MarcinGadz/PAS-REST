package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.model.Film;
import com.pas.app.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Film add(Film f) {
        if (f == null || f.getBeginTime() == null || f.getEndTime() == null
                || f.getGenre() == null || f.getBasePrice() == null
                || f.getTitle() == null || f.getBeginTime().isAfter(f.getEndTime())) {
            throw new IllegalArgumentException("Passed wrong entity");
        }
        return super.add(f);
    }

    @Override
    public Film update(UUID id, Film f) {
        if (!existsById(id)) {
            throw new NoSuchElementException("Film does not exists");
        }
        if (f.getBeginTime() == null || f.getEndTime() == null
                || f.getGenre() == null || f.getBasePrice() == null
                || f.getTitle() == null || f.getBeginTime().isAfter(f.getEndTime())) {
            throw new IllegalArgumentException("Passed wrong entity");
        }
        return super.update(id, f);
    }

    @Override
    public void remove(Film object) {
        object = getById(object.getId());
        if (object == null) {
            throw new NoSuchElementException("Film does not exists");
        }
        if (object.getTickets() == null
                || object.getTickets().isEmpty()
                || object.getEndTime().isAfter(LocalDateTime.now())) {
            super.remove(object);
        } else {
            throw new IllegalStateException("Cannot remove film");
        }
    }
}
