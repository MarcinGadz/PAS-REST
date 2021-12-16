package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.pas.app.model.*;
import com.pas.app.utils.GsonLocalDateTime;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestTicketControllerTest {


    @Test
    public void ticketControllerGetListTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        Gson gson = GsonLocalDateTime.getGsonSerializer();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        Seat seat = new Seat(2, 5, Hall.B);
        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(seat), Response.class);
        assertEquals(201, response.getStatus());
        List<Seat> seatList = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});

        User user = new User("firstname", "lastname", "login");
        user.setActive(true);
        response = target.path("api").path("user").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(user), Response.class);
        assertEquals(201, response.getStatus());
        List<User> userList = target.path("api").path("user").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});

        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);
        assertEquals(201, response.getStatus());
        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});

        Ticket ticket = new Ticket("id", userList.get(0), filmList.get(0), seatList.get(0));
        System.out.println(ticket.getClient());

        response = target.path("api").path("ticket").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(ticket)), Response.class);
        assertEquals(201, response.getStatus());


        List<Ticket> ticketList = target.path("api").path("ticket")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Ticket>>(){});
        response = target.path("api").path("ticket")
                .request(MediaType.APPLICATION_JSON).get();

        assertNotNull(ticketList);
        assertEquals(1, ticketList.size());
        assertEquals(200, response.getStatus());


        //Sprzatanie
        response = target.path("api").path("ticket").path(String.valueOf(ticketList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
        response = target.path("api").path("user").path(String.valueOf(userList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void ticketControllerGetTicketTest() {

    }
}
