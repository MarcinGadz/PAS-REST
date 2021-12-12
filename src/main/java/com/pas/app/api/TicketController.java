package com.pas.app.api;

import com.pas.app.managers.TicketManager;
import com.pas.app.model.Ticket;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("/ticket")
public class TicketController {
    private TicketManager manager;

    public TicketManager getManager() {
        return manager;
    }

    @Inject
    public void setManager(TicketManager manager) {
        this.manager = manager;
    }

    @GET
    @Produces("application/json")
    public List<Ticket> getAll() {
        return manager.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Ticket get(@PathParam("id") UUID id) {
        return manager.getById(id);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Ticket create(Ticket f) {
        System.out.println(f.getFilm());
        System.out.println(f.getSeat());
        System.out.println(f.getClient());
        return manager.add(f);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Ticket update(@PathParam("id") UUID id, Ticket f) {
        return manager.update(id, f);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        manager.remove(manager.getById(id));
    }
}
