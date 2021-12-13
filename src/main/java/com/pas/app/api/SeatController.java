package com.pas.app.api;

import com.pas.app.managers.SeatsManager;
import com.pas.app.model.Seat;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
    public Response get(@PathParam("id") UUID id) {
        Seat s = manager.getById(id);
        if (s == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(s).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Seat f) {
        if (f.getHall() == null) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
        f = manager.add(f);
        return Response.status(Response.Status.CREATED).entity(f).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, Seat f) {
        if(!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(f == null || f.getHall() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        f = manager.update(id, f);
        return Response.ok().entity(f).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        if(!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        manager.remove(manager.getById(id));
        return Response.ok().build();
    }
}
