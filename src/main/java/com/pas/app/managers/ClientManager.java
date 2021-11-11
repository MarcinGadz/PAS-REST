package com.pas.app.managers;

import com.pas.app.DAO.ClientRepository;
import com.pas.app.model.client.Client;

public class ClientManager extends ManagerGeneric<Client> {
    private ClientRepository repo;

    public ClientManager(ClientRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Client getClient(String id) {
        return repo.getClient(id);
    }
}
