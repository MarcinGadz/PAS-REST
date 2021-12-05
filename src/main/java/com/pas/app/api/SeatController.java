package com.pas.app.api;

import com.pas.app.managers.SeatsManager;
import com.pas.app.model.Seat;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("/seat")
public class SeatController {

    private SeatsManager manager;

    public SeatsManager getManager() {
        return manager;
    }

    @Inject
    public void setManager(SeatsManager manager) {
        this.manager = manager;
    }

    @GET
    @Produces("application/json")
    public List<Seat> getAll() {
        return manager.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Seat get(@PathParam("id") UUID id) {
        return manager.getById(id);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Seat create(Seat f) {
        return manager.add(f);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Seat update(@PathParam("id") UUID id, Seat f) {
        return manager.update(id, f);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        manager.remove(manager.getById(id));
    }
}
