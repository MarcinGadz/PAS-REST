package com.pas.app.managers;

import com.pas.app.DAO.TicketRepository;
import com.pas.app.model.Film;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class TicketManager extends ManagerGeneric<Ticket> {

    private TicketRepository repository;

    @Inject
    private SeatsManager seatsManager;
    @Inject
    private UserManager userManager;
    @Inject
    private FilmManager filmManager;

    public TicketManager() {
    }

    public TicketRepository getRepository() {
        return repository;
    }

    @Inject
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
                System.out.println(t);
                if (t.getSeat().equals(s) && t.getFilm().getBeginTime().isBefore(d) && t.getFilm().getEndTime().isAfter(d)) {
                    System.out.println("false");
                    return false;
                }
            }
            return true;
        }
    }
}
