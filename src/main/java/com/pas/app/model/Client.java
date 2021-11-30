package com.pas.app.model;
import com.pas.app.exceptions.ClientException;
import com.pas.app.model.Entity;

import java.util.List;
import java.util.Objects;

public class Client extends Entity {
    private String firstName;
    private String lastName;
    private String login;
    private boolean active;
    private List<Ticket> tickets;

    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client(String firstName, String lastName, String personalID) throws ClientException {
        try {
            if (Objects.equals(firstName, "") || Objects.equals(lastName, "") ||
                    Objects.equals(personalID, "")) {
                throw new ClientException();
            }
            this.firstName = firstName;
            this.lastName = lastName;
            this.login = personalID;
        } catch(ClientException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

}
