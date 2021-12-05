package com.pas.app.api;

import com.pas.app.managers.UserManager;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("/user")
public class UserController {

    @Inject
    private UserManager manager;

    @GET
    @Produces("application/json")
    public List<User> getAll() {
        return manager.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public User get(@PathParam("id") UUID id) {
        return manager.getById(id);
    }

    @GET
    @Path("/find")
    @Produces("application/json")
    public User findByLogin(@QueryParam("login") String login) {
        if(login == null) {
            return null;
        }
        return manager.getUser(login);
    }

    @GET
    @Path("/findcontaining")
    @Produces("application/json")
    public List<User> findWithCharsInLogin(@QueryParam("login") String login) {
        if(login == null) {
            return null;
        }
        return manager.getWithCharsInLogin(login);
    }

    @POST
    @Path("/{id}/activate")
    public void activate(@PathParam("id") UUID id) {
        manager.activate(id);
    }

    @POST
    @Path("/{id}/deactivate")
    public void deactivate(@PathParam("id") UUID id) {
        manager.deactivate(id);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public User create(User u) {
        return manager.register(u);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public User update(@PathParam("id") UUID id, User u) {
        return manager.update(id, u);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        manager.deactivate(id);
    }

    @GET
    @Path("/{id}/active")
    @Produces("application/json")
    public List<Ticket> getActive(@PathParam("id") UUID id) {
        return manager.getActiveTickets(id);
    }

    @GET
    @Path("/{id}/past")
    @Produces("application/json")
    public List<Ticket> getPast(@PathParam("id") UUID id) {
        return manager.getInactiveTickets(id);
    }
}
