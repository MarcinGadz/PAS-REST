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
import java.util.NoSuchElementException;
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
        User u = repo.get(login);
        if (u == null) {
            throw new NoSuchElementException("User does not exists");
        }
        return u;
    }

    public List<User> getAll() {
        return repo.getAll();
    }

    public List<User> getWithCharsInLogin(String chars) {
        return repo.getWithCharsInLogin(chars);
    }

    //D - Delete

    public synchronized void deactivate(UUID id) {
        if (!existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        repo.deactivate(id);
    }

    //C - Create
    public synchronized User register(User u) {
        if (u == null || u.getFirstName() == null || u.getLastName() == null || u.getLogin() == null) {
            throw new IllegalArgumentException("Passed wrong entity");
        }
        u.setId(UUID.randomUUID());
        u.setRole(Role.ROLE_USER);
        return repo.add(u);
    }

    public synchronized void activate(UUID id) {
        if (!existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        repo.activate(id);
    }

    public synchronized User update(UUID id, User c) {
        if (id == null || c == null || c.getFirstName() == null || c.getLastName() == null || c.getLogin() == null) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        User tmp = getById(id);
        if (tmp != null) {
            repo.remove(tmp);
            if (c.getFirstName() != null) {
                tmp.setFirstName(c.getFirstName());
            }
            if (c.getLastName() != null) {
                tmp.setLastName(c.getLastName());
            }
            repo.add(tmp);
        } else {
            throw new NoSuchElementException("User does not exists");
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
        } else {
            throw new NoSuchElementException("User does not exists");
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
        } else {
            throw new NoSuchElementException("User does not exists");
        }
        return tickets;
    }

    public List<Ticket> getAllTickets(UUID id) {
        User tmp = getById(id);
        if (tmp != null) {
            return tmp.getTickets();
        } else {
            throw new NoSuchElementException("User does not exists");
        }
    }
}
