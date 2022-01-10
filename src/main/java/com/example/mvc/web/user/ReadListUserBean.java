package com.example.mvc.web.user;

import com.example.mvc.model.Film;
import com.example.mvc.model.Ticket;
import com.example.mvc.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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
    @Inject
    EditUserBean editUserBean;
    @Inject
    ListUserTicketsBean userTicketsBean;
    public List<User> getUserList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("user").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
    }

    public void setActive(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        System.out.println(u.getId());
        target.path("api").path("user").path(String.valueOf(u.getId())).path("activate").request().put(Entity.json(u));
    }

    public void setInactive(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        System.out.println(u.getId());
        target.path("api").path("user").path(String.valueOf(u.getId())).path("deactivate").request().put(Entity.json(u));
    }

    public String editUser(User u) {
        editUserBean.setEditedUser(u);
        return "editUser";
    }

    public List<User> searchByLogin(String l) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("user").path("findcontaining").queryParam("login", l).request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});

    }

    public String getActiveTickets(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("user").path(u.getId().toString()).path("active").request().get(new GenericType<List<Ticket>>() {});
        userTicketsBean.setUserTickets(active);
        return "userTickets";
    }

    public String getInActiveTickets(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("user").path(u.getId().toString()).path("past").request().get(new GenericType<List<Ticket>>() {});
        userTicketsBean.setUserTickets(active);
        return "userTickets";
    }

    public String getAllTickets(User u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("user").path(u.getId().toString()).path("all").request().get(new GenericType<List<Ticket>>() {});
        userTicketsBean.setUserTickets(active);
        return "userTickets";
    }


    public String goBack() {
        return "main";
    }

}
