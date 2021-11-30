package com.pas.app.model;
import com.pas.app.exceptions.ClientException;
import com.pas.app.model.Entity;

import java.util.Objects;

public class Client extends Entity {
    private String firstName;
    private String lastName;
    private String login;
    private boolean active;

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
