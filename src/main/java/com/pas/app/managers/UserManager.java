package com.pas.app.managers;

import com.pas.app.DAO.UserRepository;
import com.pas.app.model.Role;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserManager {
    private UserRepository repo;

    public UserManager() {
    }

    public UserRepository getRepo() {
        return repo;
    }

    @Inject
    public void setRepo(UserRepository repo) {
        this.repo = repo;
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
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

    public synchronized void deactivate(UUID id) {
        repo.deactivate(id);
    }

    //C - Create
    public synchronized User register(User c) {
        c.setId(UUID.randomUUID());
        c.setRole(Role.ROLE_USER);
        return repo.add(c);
    }

    public synchronized void activate(UUID id) {
        repo.activate(id);
    }

    public synchronized User update(UUID id, User c) {
        User tmp = getById(id);
        repo.remove(tmp);
        if (tmp != null) {
            if (c.getFirstName() != null) {
                tmp.setFirstName(c.getFirstName());
            }
            if (c.getLastName() != null) {
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
                if (t.getFilm().getEndTime().isAfter(LocalDateTime.now())) {
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
                if (t.getFilm().getEndTime().isBefore(LocalDateTime.now())) {
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
