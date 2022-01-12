package com.example.mvc.web.user;

import com.example.mvc.model.Ticket;
import com.example.mvc.model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SessionScoped
@Named
public class SearchUserBean implements Serializable {
    private String searchedLogin = "";
    private User foundUser = new User();
    private List<User> foundUsers= new ArrayList<>();

    private boolean isList = false;

    public boolean getIsList() {
        return isList;
    }

    public String getSearchedLogin() {
        return searchedLogin;
    }

    public User getFoundUser() {
        return foundUser;
    }

    public List<User> getFoundUsers() {
        return foundUsers;
    }

    public void setIsList(boolean list) {
        isList = list;
    }

    public void setFoundUser(User foundUser) {
        this.foundUser = foundUser;
    }

    public void setSearchedLogin(String searchedLogin) {
        this.searchedLogin = searchedLogin;
    }

    public void setFoundUsers(List<User> foundUsers) {
        this.foundUsers = foundUsers;
    }

    public String searchByLogin() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        setFoundUser(target.path("api").path("user").path("find").queryParam("login", searchedLogin).request(MediaType.APPLICATION_JSON).get(User.class));
        setIsList(false);
        setSearchedLogin("");
        return "showFoundUser";
    }

    public String searchByContaining() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        setFoundUsers(target.path("api").path("user").path("findcontaining").queryParam("login", searchedLogin).request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {}));
        setIsList(true);
        setSearchedLogin("");
        return "showFoundUser";
    }

    public String goBack() {
        return "main";
    }

}
