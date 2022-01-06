package com.example.mvc.web;

import com.example.mvc.model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CreateUserBean implements Serializable {
    private User user = new User();

    public User getUser() {
        return user;
    }

    public String createUser() {
        if(getUser().getLogin() == null || getUser().getLogin().trim().equals("")) {
            return "";
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");

//        List<User> userList = target.path("api").path("user").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
         Response created = target.path("api").path("user").request(MediaType.APPLICATION_JSON).post(Entity.json(user));


        user = new User();
        return "main";
    }
}
