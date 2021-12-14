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
import java.util.UUID;

@Service
public class TicketManager extends ManagerGeneric<Ticket> {

    private TicketRepository repository;
    private SeatsManager seatsManager;
    private UserManager userManager;
    private FilmManager filmManager;

    @Autowired
    public TicketManager(FilmManager filmManager, UserManager userManager, SeatsManager seatsManager, TicketRepository repository) {
        this.filmManager = filmManager;
        this.userManager = userManager;
        this.seatsManager = seatsManager;
        this.repository = repository;
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
    public Ticket add(Ticket object) {
        User client = userManager.getById(object.getClient().getId());
        Film film = filmManager.getById(object.getFilm().getId());
        Seat s = seatsManager.getById(object.getSeat().getId());
        if(client == null || film == null || s == null) {
            throw new IllegalArgumentException("Passed wrong id");
        }
        // if repo contains ticket with the same Hall and seat and new ticket has start time between
        // start and end time of existing ticket - cannot put reservation
        synchronized (super.getLock()) {
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
    }

    @Override
    public void remove(Ticket object) {
        synchronized (super.getLock()) {
            if (object.getFilm().getEndTime().isAfter(LocalDateTime.now())) {
                object.getSeat().removeTicket(object);
                object.getClient().removeTicket(object);
                super.remove(object);
            } else throw new IllegalStateException("Cannot remove ended reservation");
        }
    }

    private boolean isSeatAvailable(Seat s, LocalDateTime d) {
        synchronized (super.getLock()) {
            for (Ticket t : getAll()) {
                if (t.getSeat().equals(s) && t.getFilm().getBeginTime().isBefore(d) && t.getFilm().getEndTime().isAfter(d)) {
                    return false;
                }
            }
            return true;
        }
    }
}
