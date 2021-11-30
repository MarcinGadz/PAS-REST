package com.pas.app.model;

import com.pas.app.model.client.Client;

import java.math.BigDecimal;

public class Ticket extends Entity {
    String ticketId;
    Client client;
    Film film;
    Seat seat;

    public Ticket(String ticketId, Client client, Film film, Seat seat) {
        this.ticketId = ticketId;
        this.client = client;
        this.film = film;
        this.seat = seat;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Client getClient() {
        return client;
    }

    public Film getFilm() {
        return film;
    }

    public Seat getSeat() {
        return seat;
    }

    public BigDecimal getFinalPrice() {
        return client.getClientType().applyDiscount(film.adjustWeekendPrice(film.getBeginTime()));
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", client=" + client +
                ", film=" + film +
                ", seat=" + seat +
                '}';
    }
}
