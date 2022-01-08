package com.example.mvc.web.seat;

import com.example.mvc.model.Seat;

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
public class CreateSeatBean implements Serializable {
    private Seat seat = new Seat();

    public Seat getSeat() {return seat;}

    public String createSeat () {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
        target.path("api").path("seat").request(MediaType.APPLICATION_JSON).post(Entity.json(seat));

        seat = new Seat();
        return "main";
    }
}
