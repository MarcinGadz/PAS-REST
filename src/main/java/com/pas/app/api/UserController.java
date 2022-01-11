package com.pas.app.api;

import com.pas.app.managers.UserManager;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Response get(@PathParam("id") UUID id) {
        try {
            User user = manager.getById(id);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/find")
    @Produces("application/json")
    public Response findByLogin(@QueryParam("login") String login) {
        if (login == null || login.trim().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
        }
        try {
            User user = manager.getUser(login);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/findcontaining")
    @Produces("application/json")
    public Response findWithCharsInLogin(@QueryParam("login") String login) {
        if (login == null || login.trim().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
        }
        List<User> users = manager.getWithCharsInLogin(login);
        return Response.ok().entity(users).build();
    }

    @PUT
    @Path("/{id}/activate")
    public Response activate(@PathParam("id") UUID id) {
        try {
            manager.activate(id);
            return Response.status(202).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/deactivate")
    public Response deactivate(@PathParam("id") UUID id) {
        try {
            manager.deactivate(id);
            return Response.status(202).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        try {
            manager.deactivate(id);
            return Response.ok().build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(User u) {
        try {
            u = manager.register(u);
            return Response.status(Response.Status.CREATED).entity(u).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, User u) {
        try {
            User tmp = manager.update(id, u);
            return Response.status(202).entity(tmp).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/active")
    @Produces("application/json")
    public Response getActive(@PathParam("id") UUID id) {
        try {
            List<Ticket> t = manager.getActiveTickets(id);
            return Response.ok().entity(t).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/past")
    @Produces("application/json")
    public Response getPast(@PathParam("id") UUID id) {
        try {
            List<Ticket> t = manager.getInactiveTickets(id);
            return Response.ok().entity(t).build();
        } catch (NoSuchElementException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
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
