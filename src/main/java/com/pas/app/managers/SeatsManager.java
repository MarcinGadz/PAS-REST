package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Seat;

public class SeatsManager extends ManagerGeneric<Seat> {

    public SeatsManager(RepositoryGeneric<Seat> repo) {
        super(repo);
    }
}
