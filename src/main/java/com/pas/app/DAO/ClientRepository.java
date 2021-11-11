package com.pas.app.DAO;

import com.pas.app.model.client.Client;

import java.util.List;

public class ClientRepository extends RepositoryGeneric<Client> {
    public Client getClient(String id) {
        List<Client> objects = getAll();
        //TODO
        return null;
    }
}
