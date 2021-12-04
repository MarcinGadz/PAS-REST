package com.pas.app.managers;

import com.pas.app.DAO.TicketRepository;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

@ApplicationScoped
public class TicketManager extends ManagerGeneric<Ticket> {

    private TicketRepository repository;

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
        // if repo contains ticket with the same Hall and seat and new ticket has start time between
        // start and end time of existing ticket - cannot put reservation
        if(isSeatAvailable(object.getSeat(), object.getFilm().getBeginTime()) && object.getClient().isActive()) {
            object.getClient().addTicket(object);
            object.getSeat().addTicket(object);
            return super.add(object);
        }
        throw new IllegalStateException("This seat is already taken by another client or client is not active");
    }

    @Override
    public void remove(Ticket object) {
        if(object.getFilm().getEndTime().after(Date.from(Instant.now()))) {
            object.getSeat().removeTicket(object);
            object.getClient().removeTicket(object);
            super.remove(object);
        }
        else throw new IllegalStateException("Cannot remove ended reservation");
    }

    private boolean isSeatAvailable(Seat s, Date d) {
        for(Ticket t : getAll()) {
            if(t.getSeat().equals(s) && t.getFilm().getBeginTime().before(d) && t.getFilm().getEndTime().after(d)) {
                return false;
            }
        }
        return true;
    }
}
