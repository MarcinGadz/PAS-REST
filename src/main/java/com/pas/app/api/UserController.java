package com.pas.app.api;

import com.pas.app.managers.UserManager;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
    public Response get(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            User user = manager.getById(id);
            return Response.status(Response.Status.OK).entity(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/find")
    @Produces("application/json")
    public Response findByLogin(@QueryParam("login") String login) {
        if (login == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = manager.getUser(login);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @GET
    @Path("/findcontaining")
    @Produces("application/json")
    public Response findWithCharsInLogin(@QueryParam("login") String login) {
        if (login == null) {
            return null;
        }
        List<User> users = manager.getWithCharsInLogin(login);
        if(users == null || users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(users).build();
    }

    @POST
    @Path("/{id}/activate")
    public Response activate(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        manager.activate(id);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/deactivate")
    public Response deactivate(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        manager.deactivate(id);
        return Response.ok().build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(User u) {
        if (u == null || u.getId() == null ||u.getFirstName() == null
                || u.getLastName() == null || u.getLogin() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong parameters").build();
        }
        try {
            u = manager.register(u);
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).entity(u).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(@PathParam("id") UUID id, User u) {
        if (id == null || u == null || u.getFirstName() == null || u.getLastName() == null || u.getLogin() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!manager.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User tmp = manager.update(id, u);
        return Response.status(Response.Status.OK).entity(tmp).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            manager.deactivate(id);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/active")
    @Produces("application/json")
    public Response getActive(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            List<Ticket> t = manager.getActiveTickets(id);
            return Response.ok().entity(t).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/past")
    @Produces("application/json")
    public Response getPast(@PathParam("id") UUID id) {
        if (manager.existsById(id)) {
            List<Ticket> t = manager.getInactiveTickets(id);
            return Response.ok().entity(t).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
