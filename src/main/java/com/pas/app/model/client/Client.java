package com.pas.app.model.client;
import com.pas.app.exceptions.ClientException;
import com.pas.app.model.Entity;

import java.util.Objects;

public class Client extends Entity {
    private String firstName;
    private String lastName;
    private String personalID;
    private ClientType clientType;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client(String firstName, String lastName, String personalID, ClientType clientType) throws ClientException {
        try {
            if (Objects.equals(firstName, "") || Objects.equals(lastName, "") ||
                    Objects.equals(personalID, "") || Objects.equals(clientType, null)) {
                throw new ClientException();
            }
            this.firstName = firstName;
            this.lastName = lastName;
            this.personalID = personalID;
            this.clientType = clientType;
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

    public String getPersonalID() {
        return personalID;
    }

    public com.pas.app.model.client.ClientType getClientType() {
        return clientType;
    }
}
