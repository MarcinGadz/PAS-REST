package com.pas.app.managers;

import com.pas.app.DAO.UserRepository;
import com.pas.app.model.Role;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;
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
public class UserManager {
    private UserRepository repo;

    public UserManager() {
    }

    public UserRepository getRepo() {
        return repo;
    }

    @Autowired
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

    public void deactivate(UUID id) {
        if (!existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        repo.deactivate(id);
    }

    //C - Create
    public User register(User u) {
        if (u == null
                || u.getFirstName() == null
                || u.getFirstName().trim().equals("")
                || u.getLastName() == null
                || u.getLastName().trim().equals("")
                || u.getLogin() == null
                || u.getLogin().trim().equals("")) {
            throw new IllegalArgumentException("Passed wrong entity");
        }

        if (u.getId() == null) {
            u.setId(UUID.randomUUID());
        }

        if (getById(u.getId()) != null) {
            throw new IllegalArgumentException("User with this ID already exists");
        }
        u.setActive(false);
        u.setRole(Role.ROLE_USER);
        return repo.add(u);
    }

    public void activate(UUID id) {
        if (!existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        repo.activate(id);
    }

    public User update(UUID id, User c) {
        if (id == null || c == null || c.getFirstName() == null || c.getLastName() == null || c.getLogin() == null) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        User tmp = getById(id);
        if (tmp != null) {
            repo.remove(tmp);
            if (c.getFirstName() != null && !c.getFirstName().trim().equals("")) {
                tmp.setFirstName(c.getFirstName());
            }
            if (c.getLastName() != null && !c.getLastName().trim().equals("")) {
                tmp.setLastName(c.getLastName());
            }
            if (c.getLastName() != null && !c.getLastName().trim().equals("")) {
                tmp.setLogin(c.getLogin());
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

