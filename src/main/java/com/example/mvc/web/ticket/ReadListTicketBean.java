package com.example.mvc.web.ticket;

import com.example.mvc.model.Ticket;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;


@SessionScoped
@Named
public class ReadListTicketBean implements Serializable {
    @Inject
    private EditTicketBean editTicketBean;

    public List<Ticket> getTicketList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("ticket").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Ticket>>() {
        });
    }

    public String deleteTicket(Ticket ticket) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        target.path("api").path("ticket").path(String.valueOf(ticket.getId())).request(MediaType.APPLICATION_JSON).delete();
        return "listTickets";
    }

    public String editTicket(Ticket t) {
        editTicketBean.setEditedTicket(t);
        return "editTicket";
    }

    public String goBack() {
        return "main";
    }

}
