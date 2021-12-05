package com.pas.app.model;

import java.util.List;
import java.util.Objects;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String login;
    private boolean active;
    private List<Ticket> tickets;
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String personalID) {
        if (Objects.equals(firstName, "") || Objects.equals(lastName, "") ||
                Objects.equals(personalID, "")) {
            throw new IllegalArgumentException();
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = personalID;
        this.role = Role.ROLE_USER;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    public void removeTicket(Ticket t) {
        tickets.remove(t);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
