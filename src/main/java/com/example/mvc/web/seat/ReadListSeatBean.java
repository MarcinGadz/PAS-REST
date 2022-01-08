package com.example.mvc.web.seat;

import com.example.mvc.model.Seat;

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

    public String goBack() { return "main";}

}
