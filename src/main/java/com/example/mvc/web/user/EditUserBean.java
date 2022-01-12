package com.example.mvc.web.user;

import com.example.mvc.model.Film;
import com.example.mvc.model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@SessionScoped
@Named
public class EditUserBean implements Serializable {
    private User editedUser;

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
    }

    public String editUser() {
        if(editedUser != null) {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081");
            target.path("api").path("user").path(String.valueOf(editedUser.getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(editedUser));
            editedUser = null;
        } else {
            throw new IllegalArgumentException("Proba pominiecia");
        }
        return "main";
    }
}
