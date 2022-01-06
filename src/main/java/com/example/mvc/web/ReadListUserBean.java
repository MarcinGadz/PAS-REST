package com.example.mvc.web;

import com.example.mvc.model.Ticket;
import com.example.mvc.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SessionScoped
@Named
public class ReadListUserBean implements Serializable {

    public List<User> getUserList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("user").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
    }

    public void remove(User u) {
        //TODO delete in rest
        //rest.delete()

    }

    public void setActive(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        System.out.println(u.getId());
        target.path("api").path("user").path(String.valueOf(u.getId())).path("activate").request().put(Entity.json(u));
    }

    public List<Ticket> getActiveTickets(User u) {
        //TODO
        //rest.getActiveTickets
        return null;
    }

    public List<Ticket> getInActiveTickets(User u) {
        //TODO
        //rest.getActiveTickets
        return null;
    }

}
