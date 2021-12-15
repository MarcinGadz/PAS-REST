package com.pas.app.api;

import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Path("/film")
public class FilmController {
    @Inject
    private FilmManager manager;

    @GET
    @Produces("application/json")
    public List<Film> getAll() {
        return manager.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") UUID id) {
        try {
            Film s = manager.getById(id);
            return Response.ok().entity(s).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Film f) {
        try {
            f = manager.add(f);
            return Response.status(Response.Status.CREATED).entity(f).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, Film f) {
        try {
            f = manager.update(id, f);
            return Response.ok().entity(f).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        try {
            manager.remove(manager.getById(id));
            return Response.ok().build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
