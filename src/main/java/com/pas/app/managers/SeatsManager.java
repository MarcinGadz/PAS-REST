package com.pas.app.managers;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.DAO.SeatRepository;
import com.pas.app.model.Entity;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SeatsManager extends ManagerGeneric<Seat> {
    private SeatRepository repository;

    public SeatsManager() {
    }

    public SeatRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(SeatRepository repository) {
        this.repository = repository;
        super.setRepo(repository);
    }


    @Override
    public synchronized Seat add(Seat f) {
        if (f.getHall() == null || f.getRow() < 0 || f.getColumn() < 0) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        return super.add(f);
    }

    @Override
    public synchronized void remove(Seat object) {
        // If there aren't active reservations with this object - remove
        object = getById(object.getId());
        if (object == null) {
            throw new NoSuchElementException("Seat does not exists");
        }
        if (getActiveTickets(object.getId()).isEmpty()) {
            super.remove(object);
        } else {
            throw new IllegalStateException("Cannot remove seat with active reservations");
        }
    }

    @Override
    public synchronized Seat update(UUID id, Seat c) {
        if (c == null || c.getHall() == null || c.getRow() < 0 || c.getColumn() < 0) {
            throw new IllegalArgumentException("Cannot update with passed values");
        }
        Seat tmp = getById(id);
        if (tmp == null) {
            throw new NoSuchElementException("Seat does not exists");
        }
        tmp.setHall(c.getHall());
        tmp.setColumn(c.getColumn());
        tmp.setRow(c.getRow());
        return super.update(id, c);
    }

    public List<Ticket> getActiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        Seat tmp = getById(id);
        if (tmp != null && tmp.getTicketList() != null) {
            tmp.getTicketList().forEach(t -> {
                if (t.getFilm().getEndTime().isAfter(LocalDateTime.now())) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

    public List<Ticket> getInactiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        Seat tmp = getById(id);
        if (tmp != null && tmp.getTicketList() != null) {
            tmp.getTicketList().forEach(t -> {
                if (t.getFilm().getEndTime().isBefore(LocalDateTime.now())) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

}