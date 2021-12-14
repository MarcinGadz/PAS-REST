package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.model.Film;
import com.pas.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class FilmManager extends ManagerGeneric<Film> {

    private FilmRepository repository;

    public FilmManager() {

    }

    public FilmRepository getRepository() {
        return repository;
    }

    @Autowired
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


    @Override
    public void remove(Film object) {
        if(object.getTickets() == null || object.getTickets().isEmpty() || object.getEndTime().isBefore(LocalDateTime.now())) {
            super.remove(object);
        } else {
            throw new IllegalStateException("Cannot remove film");
        }
    }
}
