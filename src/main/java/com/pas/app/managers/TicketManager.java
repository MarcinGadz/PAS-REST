package com.pas.app.managers;

import com.pas.app.DAO.TicketRepository;
import com.pas.app.model.Film;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TicketManager extends ManagerGeneric<Ticket> {

    private TicketRepository repository;

    @Autowired
    private SeatsManager seatsManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private FilmManager filmManager;

    public TicketManager() {
    }

    public TicketRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(TicketRepository repository) {
        this.repository = repository;
        super.setRepo(repository);
    }

    @Override
    public synchronized Ticket add(Ticket object) {
        if (object.getFilm() == null || object.getSeat() == null || object.getClient() == null
                || object.getFilm().getId() == null || object.getSeat().getId() == null
                || object.getClient().getId() == null) {
            throw new IllegalArgumentException("Passed wrong arguments");
        }

        User client = userManager.getById(object.getClient().getId());
        Film film = filmManager.getById(object.getFilm().getId());
        Seat s = seatsManager.getById(object.getSeat().getId());
        if (client == null || film == null || s == null) {
            throw new IllegalArgumentException("Passed wrong id");
        }

        // if repo contains ticket with the same Hall and seat and new ticket has start time between
        // start and end time of existing ticket - cannot put reservation
        if (isSeatAvailable(s, film.getBeginTime()) && client.isActive()) {
            object.setId(UUID.randomUUID());
            client.addTicket(object);
            s.addTicket(object);
            object.setClient(client);
            object.setFilm(film);
            object.setSeat(s);
            return super.add(object);
        }
        throw new IllegalStateException("This seat is already taken by another client or client is not active");
    }

    @Override
    public synchronized void remove(Ticket object) {
        if (object == null) {
            throw new NoSuchElementException("Ticket does not exists");
        }
        if (object.getFilm().getEndTime().isAfter(LocalDateTime.now())) {
            object.getSeat().removeTicket(object);
            object.getClient().removeTicket(object);
            super.remove(object);
        } else throw new IllegalStateException("Cannot remove ended reservation");
    }

    @Override
    public synchronized Ticket update(UUID id, Ticket obj) {
        Ticket tmp = getById(id);
        if (tmp == null) {
            throw new NoSuchElementException("Ticket does not exists");
        }
        if(tmp.getFilm().getBeginTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot edit expired ticket");
        }
        if(obj.getClient().getId() == null
                || obj.getFilm().getId() == null || obj.getSeat() == null) {
            throw new IllegalArgumentException("Wrong arguments");
        }
        return super.update(id, obj);
    }

    private synchronized boolean isSeatAvailable(Seat s, LocalDateTime d) {
        for (Ticket t : getAll()) {
            if (t.getSeat().equals(s) && t.getFilm().getBeginTime().isBefore(d) && t.getFilm().getEndTime().isAfter(d)) {
                return false;
            }
        }
        return true;
    }
}
