package com.pas.app.DAO;

import com.pas.app.model.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientRepository extends RepositoryGeneric<Client> {
    public ClientRepository() {
        //TODO add init data to repo
    }

    public Client getClient(String login) {
        return super.getAll().stream().filter(c -> c.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<Client> getWithCharsInLogin(String chars) {
        return super.getAll().stream().filter(c -> c.getLogin().contains(chars)).collect(Collectors.toList());
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
