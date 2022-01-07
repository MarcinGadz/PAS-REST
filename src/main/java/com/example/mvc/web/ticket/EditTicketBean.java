package com.example.mvc.web.ticket;

import com.example.mvc.model.Ticket;

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
public class EditTicketBean implements Serializable {
    private Ticket editedTicket;

    public Ticket getEditedTicket() {
        return editedTicket;
    }

    public void setEditedTicket(Ticket editedTicket) {
        this.editedTicket = editedTicket;
    }

    public String editTicket() {
        if (editedTicket != null) {
            System.out.println(editedTicket);
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081");
            target.path("api").path("ticket").path(String.valueOf(editedTicket.getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(editedTicket));
            editedTicket = null;
        } else {
            throw new IllegalArgumentException("Proba pominiecia");
        }
        return "main";
    }

}
