package com.pas.app.api;

import com.pas.app.managers.TicketManager;
import com.pas.app.model.Ticket;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
    public Response get(@PathParam("id") UUID id) {
        Ticket t = manager.getById(id);
        if(t == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
        return Response.ok().entity(t).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Ticket f) {
        if(f.getFilm() == null || f.getSeat() == null || f.getClient() == null
                || f.getFilm().getId() == null || f.getSeat().getId() == null
        || f.getClient().getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong parameters").build();
        }
        try {
            f = manager.add(f);
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).entity(f).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, Ticket f) {
        if(!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
        if(f == null || f.getClient().getId() == null || f.getFilm().getId() == null || f.getSeat() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong parameters").build();
        }
        f = manager.update(id, f);
        return Response.ok().entity(f).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        if(!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
        try {
            manager.remove(manager.getById(id));
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return Response.ok().build();
    }
}
