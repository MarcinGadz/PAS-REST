package com.pas.app.api;

import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    public Film get(@PathParam("id") UUID id) {
        System.out.println("Id to: " + id);
        return manager.getById(id);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Film create(Film f) {
        return manager.add(f);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Film update(@PathParam("id") UUID id, Film f) {
        return manager.update(id, f);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        manager.remove(manager.getById(id));
    }
}
