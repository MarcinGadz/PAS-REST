package com.pas.app.managers;

import com.pas.app.DAO.UserRepository;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager {
    private final UserRepository repo;

    @Inject
    public UserManager(UserRepository repo) {
        this.repo = repo;
    }

    //R - Read
    public User getById(UUID id) {
        return repo.getById(id);
    }

    //R - Read
    public User getUser(String login) {
        return repo.get(login);
    }

    public List<User> getAll() {
        return repo.getAll();
    }

    public List<User> getWithCharsInLogin(String chars) {
        return repo.getWithCharsInLogin(chars);
    }

    //D - Delete

    public void deactivate(User c) {
        repo.deactivate(c);
    }

    //C - Create
    public void register(User c) {
        repo.add(c);
    }

    public void activate(User c) {
        repo.activate(c);
    }

    public User update(UUID id, User c) {
        User tmp = getById(id);
        repo.remove(tmp);
        if (tmp != null) {
            if(c.getFirstName() != null) {
                tmp.setFirstName(c.getFirstName());
            }
            if(c.getLastName() != null) {
                tmp.setLastName(c.getLastName());
            }
            repo.add(tmp);
        }
        return tmp;
    }

    public List<Ticket> getActiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        User tmp = getById(id);
        if (tmp != null) {
            tmp.getTickets().forEach(t -> {
                if(t.getFilm().getEndTime().after(Date.from(Instant.now()))) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

    public List<Ticket> getInactiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        User tmp = getById(id);
        if (tmp != null) {
            tmp.getTickets().forEach(t -> {
                if(t.getFilm().getEndTime().before(Date.from(Instant.now()))) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

    public List<Ticket> getAllTickets(UUID id) {
        User tmp = getById(id);
        if (tmp != null) {
            return tmp.getTickets();
        }
        return new ArrayList<>();
    }
}
