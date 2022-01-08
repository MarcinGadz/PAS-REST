package com.example.mvc.web.seat;

import com.example.mvc.model.Film;
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
public class EditSeatBean implements Serializable {
    private Seat editedSeat;

    public Seat getEditedSeat() {
        return editedSeat;
    }

    public void setEditedSeat(Seat editedSeat) {
        this.editedSeat = editedSeat;
    }

    public String editSeat() {
        if(editedSeat != null) {
            System.out.println(editedSeat);
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081");
            target.path("api").path("seat").path(String.valueOf(editedSeat.getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(editedSeat));
            editedSeat = null;
        } else {
            throw new IllegalArgumentException("Proba pominiecia");
        }
        return "main";
    }

}
