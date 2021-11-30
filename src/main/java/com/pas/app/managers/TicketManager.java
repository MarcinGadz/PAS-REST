package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;

import java.util.Date;

public class TicketManager extends ManagerGeneric<Ticket> {
    public TicketManager(RepositoryGeneric<Ticket> repo) {
        super(repo);
    }

    @Override
    public Ticket add(Ticket object) {
        // if repo contains ticket with the same Hall and seat and new ticket has start time beetween
        // start and end time of existing ticket - cannot put reservation
        if(isSeatAvailable(object.getSeat(), object.getFilm().getBeginTime())) {
            return super.add(object);
        }
        throw new IllegalStateException("This seat is already taken by another client");
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
