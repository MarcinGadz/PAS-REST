package com.pas.app.model;


public class Ticket extends Entity {
    private User user;
    private Film film;
    private Seat seat;

    public Ticket() {
    }

    public Ticket(User user, Film film, Seat seat) {
        this.user = user;
        this.film = film;
        this.seat = seat;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public Film getFilm() {
        return film;
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "client=" + user +
                ", film=" + film +
                ", seat=" + seat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
