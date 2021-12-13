package com.pas.app.api;

import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
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
        if (!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
        Film s = manager.getById(id);
        return Response.ok().entity(s).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Film f) {
        if (f == null || f.getBeginTime() == null || f.getEndTime() == null || f.getId() == null
        || f.getGenre() == null || f.getBasePrice() == null || f.getTitle() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong parameters").build();
        }
        f = manager.add(f);
        return Response.status(Response.Status.CREATED).entity(f).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, Film f) {
        if(!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
        if (f == null || f.getBeginTime() == null || f.getEndTime() == null
                || f.getGenre() == null || f.getBasePrice() == null || f.getTitle() == null) {
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
