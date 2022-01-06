package com.example.mvc.web.film;

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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SessionScoped
@Named
public class CreateFilmBean implements Serializable {
    private Film film = new Film();

    public Film getFilm() {return film;}

    public String createFilm() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
//        TODO: Naprawic daty bo nie przejdzie
        target.path("api").path("film").request(MediaType.APPLICATION_JSON).post(Entity.json(film));

        film = new Film();
        return "main";
    }

//    public LocalDateTime convertDate (Date time) {
//        return LocalDateTime.parse(new SimpleDateFormat("dd-MM-yyyy").format(time));
//    }
}
