package com.example.mvc.web.user;

import com.example.mvc.model.Ticket;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ListUserTicketsBean implements Serializable {
    private List<Ticket> userTickets;

    public List<Ticket> getUserTickets() {
        return userTickets;
    }

    public void setUserTickets(List<Ticket> userTickets) {
        this.userTickets = userTickets;
    }

    public String goBack() {
        return "main";
    }
}
