package com.pas.app.managers;

import com.pas.app.DAO.ClientRepository;
import com.pas.app.model.Ticket;
import com.pas.app.model.Client;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientManager {
    private final ClientRepository repo;

    public ClientManager(ClientRepository repo) {
        this.repo = repo;
    }

    //R - Read
    public Client getById(UUID id) {
        return repo.getById(id);
    }

    //R - Read
    public Client getClient(String login) {
        return repo.getClient(login);
    }

    public List<Client> getAllClients() {
        return repo.getAll();
    }

    public List<Client> getClientsWithCharsInLogin(String chars) {
        return repo.getWithCharsInLogin(chars);
    }

    //D - Delete
    public void unregisterClient(UUID id) {
        repo.remove(repo.getById(id));
    }

    public void deactivateClient(Client c) {
        repo.deactivateClient(c);
    }

    //C - Create
    public void registerClient(Client c) {
        repo.add(c);
    }

    public void activateClient(Client c) {
        repo.activateClient(c);
    }

    public void updateClient(UUID id, Client c) {
        Client tmp = getById(id);
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
    }

    public List<Ticket> getActiveTickets(UUID id) {
        List<Ticket> tickets = new ArrayList<>();
        Client tmp = getById(id);
        if (tmp != null) {
            tmp.getTickets().forEach(t -> {
                if(t.getFilm().getEndTime().after(Date.from(Instant.now()))) {
                    tickets.add(t);
                }
            });
        }
        return tickets;
    }

    public List<Ticket> getAllTickets(UUID id) {
        Client tmp = getById(id);
        if (tmp != null) {
            return tmp.getTickets();
        }
        return new ArrayList<>();
    }
}
