package com.pas.app.model.client;


import com.pas.app.exceptions.ClientException;

import java.util.Objects;

public class Client {
    String firstName;
    String lastName;
    String personalID;
    ClientType clientType;

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
