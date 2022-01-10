package com.example.mvc.web.seat;

import com.example.mvc.model.Seat;
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
public class ReadListSeatBean implements Serializable {
    @Inject
    private EditSeatBean editSeatBean;

    @Inject
    ListSeatTicketsBean seatTicketsBean;

    public List<Seat> getSeatList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
    }

    public String deleteSeat(Seat s) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        target.path("api").path("seat").path(String.valueOf(s.getId())).request(MediaType.APPLICATION_JSON).delete();
        return "listSeats";
    }

    public String editSeat(Seat s) {
        editSeatBean.setEditedSeat(s);
        return "editSeat";
    }

    public String getActiveTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("active").request().get(new GenericType<List<Ticket>>() {});
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String getInActiveTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("past").request().get(new GenericType<List<Ticket>>() {});
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String getAllTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("all").request().get(new GenericType<List<Ticket>>() {});
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String goBack() { return "main";}

}
