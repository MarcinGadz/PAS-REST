package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.DAO.SeatRepository;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SeatsManager extends ManagerGeneric<Seat> {
    private SeatRepository repository;

    public SeatRepository getRepository() {
        return repository;
    }

    @Inject
    public void setRepository(SeatRepository repository) {
        this.repository = repository;
        super.setRepo(repository);
    }

    public SeatsManager() {
    }

    @Override
    public void remove(Seat object) {
        // If there aren't active reservations with this object - remove
        if (getActiveTickets(object.getId()).isEmpty()) {
            super.remove(object);
        } else throw new IllegalStateException("Cannot remove seat with active reservations");
    }

    @Override
    public Seat update(UUID id, Seat c) {
        Seat tmp = getById(id);
        remove(tmp);
        if (tmp != null) {
            tmp.setHall(c.getHall());
            tmp.setColumn(c.getColumn());
            tmp.setRow(c.getRow());
            add(tmp);
        }
        return tmp;
    }

    public List<Ticket> getActiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        Seat tmp = getById(id);
        if (tmp != null) {
            tmp.getTicketList().forEach(t -> {
                if (t.getFilm().getEndTime().isAfter(LocalDateTime.from(Instant.now()))) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

    public List<Ticket> getInactiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        Seat tmp = getById(id);
        if (tmp != null) {
            tmp.getTicketList().forEach(t -> {
                if (t.getFilm().getEndTime().isBefore(LocalDateTime.from(Instant.now()))) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

}
