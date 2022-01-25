package com.example.mvc.web.seat;

import com.example.mvc.model.Seat;
import com.example.mvc.model.Ticket;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@SessionScoped
@Named
public class ReadListSeatBean implements Serializable {
    @Inject
    ListSeatTicketsBean seatTicketsBean;
    @Inject
    private EditSeatBean editSeatBean;


    public List<Seat> getSeatList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {
        });
    }

    public String deleteSeat() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String seattmp = params.get("seatid");
        System.out.println(seattmp);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
//        target.path("api").path("seat").path(String.valueOf(s.getId())).request(MediaType.APPLICATION_JSON).delete();
        target.path("api").path("seat").path(seattmp).request(MediaType.APPLICATION_JSON).delete();
        return null;
    }

    public String editSeat(Seat s) {
        editSeatBean.setEditedSeat(s);
        return "editSeat";
    }

    public String getActiveTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("active").request().get(new GenericType<List<Ticket>>() {
        });
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String getInActiveTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("past").request().get(new GenericType<List<Ticket>>() {
        });
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String getAllTickets(Seat u) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        List<Ticket> active = target.path("api").path("seat").path(u.getId().toString()).path("all").request().get(new GenericType<List<Ticket>>() {
        });
        seatTicketsBean.setSeatTickets(active);
        return "seatTickets";
    }

    public String goBack() {
        return "main";
    }

}
