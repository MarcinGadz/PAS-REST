package com.example.mvc.web.seat;

import com.example.mvc.model.Ticket;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ListSeatTicketsBean implements Serializable {
    private List<Ticket> seatTickets;

    public List<Ticket> getSeatTickets() {
        return seatTickets;
    }

    public void setSeatTickets(List<Ticket> seatTickets) {
        this.seatTickets = seatTickets;
    }

    public String goBack() {
        return "main";
    }
}
