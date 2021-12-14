package service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.gson.Gson;
import com.pas.app.managers.FilmManager;
import com.pas.app.model.*;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.glassfish.hk2.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RestServiceTests {

    @Test
    public void filmListResponseTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        Response responseBad = target.path("api").path("films").request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, responseBad.getStatus());
    }

    @Test
    public void testListFilmsController() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");

        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});

        assertNotNull(filmList);
        assertEquals(1, filmList.size());
        assertEquals("Title", filmList.get(0).getTitle());
        assertEquals("Genre", filmList.get(0).getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), filmList.get(0).getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), filmList.get(0).getEndTime());
        assertEquals(BigDecimal.valueOf(100), filmList.get(0).getBasePrice());
        assertNotNull(filmList.get(0).getId());
    }

    @Test
    public void filmResponseTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
        Response response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId())).request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        Response responseBad = target.path("api").path("films").path("33").request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, responseBad.getStatus());
    }

    @Test
    public void testFilmController() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");

        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
        Film film = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).get(Film.class);

        assertEquals("Title", film.getTitle());
        assertEquals("Genre", film.getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), film.getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), film.getEndTime());
        assertEquals(BigDecimal.valueOf(100), film.getBasePrice());
        assertNotNull(film.getId());

//"film").path("71cedddb-dd2e-4c63-a645-d143f245e62a").request(MediaType.APPLICATION_JSON).get(Film.class);
//            System.out.println(filmTest.getId());

    }

    @Test
    public void postFilmTest() {
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
//        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
//        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Gson gson = new Gson();
        System.out.println(gson.toJson(tempFilm));
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tempFilm), Response.class);
//        System.out.println(Entity.json(tempFilm));

        List<Film> filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {
                });
        System.out.println(filmList);
        System.out.println(response.getStatusInfo());
    }

//    @Test
//    public void deleteFilmTest() {
//        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
//        Client client = clientBuilder.build();
//
//        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
//
//        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
//        System.out.println(filmList);
//        assertEquals(2, filmList.size());
//        target.path("api").path("film").path(String.valueOf(filmList.get(1).getId())).request(MediaType.APPLICATION_JSON).delete();
//        List<Film> filmListAfterDelete = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
//        assertEquals(1, filmListAfterDelete.size());
//    }

    @Test
    public void putFilmTest() {
        Film tempFilm = new Film(
                "TitleNEW",
                "GenreNEW",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        List<Film> filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {
                });
        System.out.println(filmList);
        Response response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId())).request(MediaType.APPLICATION_JSON)
                .put(Entity.json(tempFilm), Response.class);

        System.out.println(filmList);
        System.out.println(response.getStatusInfo());

    }


}
