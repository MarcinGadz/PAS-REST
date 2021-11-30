package com.pas.app.model;

import java.math.BigDecimal;

public class Ticket extends Entity {
    private String ticketId;
    private Client client;
    private Film film;
    private Seat seat;

    public Ticket(String ticketId, Client client, Film film, Seat seat) {
        this.ticketId = ticketId;
        this.client = client;
        this.film = film;
        this.seat = seat;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setSeat(Seat seat) {
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
        return film.adjustWeekendPrice(film.getBeginTime());
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
