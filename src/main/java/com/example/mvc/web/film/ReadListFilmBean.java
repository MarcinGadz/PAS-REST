package com.example.mvc.web.film;

import com.example.mvc.model.Film;
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
import java.util.List;


@SessionScoped
@Named
public class ReadListFilmBean implements Serializable {
    @Inject
    private EditFilmBean editFilmBean;

    public List<Film> getFilmList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        return target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
    }

    public String deleteFilm(Film f) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/");
        target.path("api").path("film").path(String.valueOf(f.getId())).request(MediaType.APPLICATION_JSON).delete();
        return "listFilms";
    }

    public String editFilm(Film f) {
        editFilmBean.setEditedFilm(f);
        return "editFilm";
    }




    public String goBack() { return "main";}

}
