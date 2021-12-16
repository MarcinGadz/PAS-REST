package service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.gson.*;
import com.pas.app.managers.FilmManager;
import com.pas.app.model.*;
import com.pas.app.utils.GsonLocalDateTime;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.glassfish.hk2.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RestFilmControllerTest {

    @Test
    public void testListFilmsController() {
        //dodanie obiektu do testow
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Gson gson = GsonLocalDateTime.getGsonSerializer();

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");

        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);

        //Test
        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});

        assertEquals(201, response.getStatus());
        assertNotNull(filmList);
        assertEquals(1, filmList.size());
        assertEquals("Title", filmList.get(0).getTitle());
        assertEquals("Genre", filmList.get(0).getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), filmList.get(0).getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), filmList.get(0).getEndTime());
        assertEquals(BigDecimal.valueOf(100), filmList.get(0).getBasePrice());
        assertNotNull(filmList.get(0).getId());

        Response responseGood = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, responseGood.getStatus());

        Response responseBad = target.path("api").path("films").request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, responseBad.getStatus());

        //sprzatanie po tescie
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void filmResponseTest() {
        //dodanie obiektu do testow
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        Gson gson = GsonLocalDateTime.getGsonSerializer();
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);

        //Test
        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId())).request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        Response responseBad = target.path("api").path("films").path("33").request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, responseBad.getStatus());

        //sprzatanie po tescie
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testFilmController() {
        //dodanie obiektu do testow
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        Gson gson = GsonLocalDateTime.getGsonSerializer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);

        //Test
        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Film>>() {});
        Film film = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).get(Film.class);

        assertEquals("Title", film.getTitle());
        assertEquals("Genre", film.getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), film.getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), film.getEndTime());
        assertEquals(BigDecimal.valueOf(100), film.getBasePrice());
        assertNotNull(film.getId());

        //sprzatanie po tescie
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
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


        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Gson gson = GsonLocalDateTime.getGsonSerializer();

        List<Film> filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>(){});
        assertEquals(0, filmList.size());

        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);

        filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
        assertEquals(1, filmList.size());
        assertEquals(201, response.getStatus());

        //sprzatanie po tescie
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void deleteFilmTest() {
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        Gson gson = GsonLocalDateTime.getGsonSerializer();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        //dodawanie filmu do usuniecia
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);
        assertEquals(201, response.getStatus());

        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});
        assertEquals(1, filmList.size());

        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId())).request(MediaType.APPLICATION_JSON).delete();
        List<Film> filmListAfterDelete = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});

        assertEquals(200, response.getStatus());
        assertEquals(0, filmListAfterDelete.size());
    }

    @Test
    public void putFilmTest() {
        Film tempFilm = new Film(
                "Title",
                "Genre",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        Film tempFilm2 = new Film(
                "TitleNEW",
                "GenreNEW",
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
                LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
                BigDecimal.valueOf(100)
        );
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        Gson gson = GsonLocalDateTime.getGsonSerializer();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Response response = target.path("api").path("film").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(gson.toJson(tempFilm)), Response.class);

        List<Film> filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {
                });
        assertEquals(1, filmList.size());
        assertEquals("Title", filmList.get(0).getTitle());
        assertEquals("Genre", filmList.get(0).getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), filmList.get(0).getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), filmList.get(0).getEndTime());
        assertEquals(BigDecimal.valueOf(100), filmList.get(0).getBasePrice());
        assertNotNull(filmList.get(0).getId());

        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(gson.toJson(tempFilm2)), Response.class);
        filmList = target.path("api").path("film").
                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {
                });
        assertEquals(200, response.getStatus());
        assertEquals(1, filmList.size());
        assertEquals("TitleNEW", filmList.get(0).getTitle());
        assertEquals("GenreNEW", filmList.get(0).getGenre());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), filmList.get(0).getBeginTime());
        assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), filmList.get(0).getEndTime());
        assertEquals(BigDecimal.valueOf(100), filmList.get(0).getBasePrice());
        assertNotNull(filmList.get(0).getId());

        //sprzatanie po tescie
        response = target.path("api").path("film").path(String.valueOf(filmList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }


}
