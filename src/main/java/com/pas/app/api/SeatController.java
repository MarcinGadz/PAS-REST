package com.pas.app.api;

import com.pas.app.managers.SeatsManager;
import com.pas.app.model.Seat;
import com.pas.app.model.Ticket;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Response get(@PathParam("id") UUID id) {
        try {
            Seat s = manager.getById(id);
            return Response.ok().entity(s).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Seat f) {
        try {
            f = manager.add(f);
            return Response.status(Response.Status.CREATED).entity(f).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong parameters").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, Seat f) {
        try {
            f = manager.update(id, f);
            return Response.status(202).entity(f).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        try {
            manager.remove(manager.getById(id));
            return Response.status(202).build();
        } catch (IllegalStateException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/active")
    public Response getActive(@PathParam("id") UUID id) {
        try {
            List<Ticket> t = manager.getActiveTickets(id);
            return Response.ok(t).build();
        } catch (NoSuchElementException ex) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/{id}/past")
    public Response getPast(@PathParam("id") UUID id) {
        try {
            List<Ticket> t = manager.getInactiveTickets(id);
            return Response.ok(t).build();
        } catch (NoSuchElementException ex) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/{id}/all")
    public Response getAllTickets(@PathParam("id") UUID id) {
        try {
            List<Ticket> t = manager.getAllTickets(id);
            return Response.ok(t).build();
        } catch (NoSuchElementException ex) {
            return Response.status(404).build();
        }
    }
}
