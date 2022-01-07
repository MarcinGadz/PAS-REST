package com.example.mvc.web.ticket;

import com.example.mvc.model.Film;
import com.example.mvc.model.Seat;
import com.example.mvc.model.Ticket;
import com.example.mvc.model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@SessionScoped
@Named
public class CreateTicketBean implements Serializable {

    private Ticket createNewTicket() {
        Ticket t = new Ticket();
        t.setFilm(new Film());
        t.setSeat(new Seat());
        t.setUser(new User());
        return t;
    }

    private Ticket ticket = createNewTicket();

    public Ticket getTicket() {
        return ticket;
    }

    public String createTicket() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
        target.path("api").path("ticket").request(MediaType.APPLICATION_JSON).post(Entity.json(ticket));
        ticket = createNewTicket();
        return "main";
    }

}
