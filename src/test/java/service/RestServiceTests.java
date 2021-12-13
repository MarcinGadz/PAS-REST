package service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;
import com.pas.app.model.Hall;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
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
    public void testListFilmsController() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");

        List<Film> filmList = target.path("api").path("film").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Film>>() {});

        assertNotNull(filmList);
        assertEquals(2, filmList.size());
        assertEquals("Title", filmList.get(0).getTitle());
        assertEquals("Genre", filmList.get(0).getGenre());

//        assertEquals(200, target.path("api").request().stat);
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/pas-rest-1.0-SNAPSHOT/api/film")).build();
////        HttpUriRequest request = new HttpGet( "http://localhost:8080/pas-rest-1.0-SNAPSHOT/api/film" );
//        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//        fail("FIXME");
//        HttpResponse response = HttpClientBuilder.create().build().execute( request );

    }

    @Test
    public void testFilmController() {
//        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            JacksonJsonProvider provider = new JacksonJsonProvider(mapper);

            FilmManager filmManager = new FilmManager();


//            Film filmTest = new Film(
//                    "Title",
//                    "Genre",
//                    LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)),
//                    LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)),
//                    BigDecimal.valueOf(100)
//            );

//            filmManager.add(filmTest);
//            System.out.println(filmManager.getAll());


//            Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
//            WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");

//            Film film = target.path("api").path("film").path("71cedddb-dd2e-4c63-a645-d143f245e62a").request(MediaType.APPLICATION_JSON).get(Film.class);
//            System.out.println(filmTest.getId());
//
//            assertNotNull(film);
//            assertEquals(UUID.fromString("71cedddb-dd2e-4c63-a645-d143f245e62a"), film.getId());
//            assertEquals(new BigDecimal(100), film.getBasePrice());
//            assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)), film.getBeginTime());
//            assertEquals(LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)), film.getEndTime());
//            assertEquals("Title", film.getTitle());
//            assertEquals("Genre", film.getGenre());
//        } catch (Error e) {
//            System.out.println(e);
//
//        }
    }


}
