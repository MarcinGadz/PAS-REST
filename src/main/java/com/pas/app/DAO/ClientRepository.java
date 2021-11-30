package com.pas.app.DAO;

import com.pas.app.model.client.Client;

import java.util.List;

public class ClientRepository extends RepositoryGeneric<Client> {
    public ClientRepository() {
        //TODO add init data to repo
    }

    public Client getClient(String login) {
        return super.getAll().stream().filter(c -> c.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<Client> getWithCharsInLogin(String chars) {
        return super.getAll().stream().filter(c -> c.getLogin().contains(chars)).toList();
    }

    public void activateClient(Client c) {
        c = getById(c.getId());
//        remove(c);
        c.setActive(true);
//        add(c);
    }

    public void deactivateClient(Client c) {
        c = getById(c.getId());
        if (c != null) {
            c.setActive(false);
        }
    }
}
