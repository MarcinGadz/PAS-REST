package com.pas.app.model.client;

import com.pas.app.model.Entity;

public class Client extends Entity {
    private String login;
    private boolean isActive;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
