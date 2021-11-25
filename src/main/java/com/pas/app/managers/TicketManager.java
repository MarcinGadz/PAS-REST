package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Ticket;

public class TicketManager extends ManagerGeneric<Ticket> {
    public TicketManager(RepositoryGeneric<Ticket> repo) {
        super(repo);
    }
}
