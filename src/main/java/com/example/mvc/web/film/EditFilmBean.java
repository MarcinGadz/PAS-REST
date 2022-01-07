package com.example.mvc.web.film;

import com.example.mvc.model.Film;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SessionScoped
@Named
public class EditFilmBean implements Serializable {
    private Film editedFilm;

    public Film getEditedFilm() {
        return editedFilm;
    }

    public void setEditedFilm(Film editedFilm) {
        this.editedFilm = editedFilm;
    }

    public String editFilm() {
        if(editedFilm != null) {
            System.out.println(editedFilm);
//            TODO: Zeby dzialalo trzeba zmienic te daty w film, tak to reszta dziala
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081");
            target.path("api").path("film").path(String.valueOf(editedFilm.getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(editedFilm));
            editedFilm = null;
        } else {
            throw new IllegalArgumentException("Proba pominiecia");
        }
        return "main";
    }

}
