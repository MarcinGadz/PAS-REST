package service;

import com.google.gson.Gson;
import com.pas.app.model.Film;
import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
import com.pas.app.utils.GsonLocalDateTime;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestSeatContollerTest {

    @Test
    public void templateTestu() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        Gson gson = GsonLocalDateTime.getGsonSerializer();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
    }

    @Test
    public void seatControllerGetListTest() {
        Seat tmp = new Seat(2, 5, Hall.B);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8081");

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);

        assertEquals(201, response.getStatus());

        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
        response = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get();

        assertNotNull(seatList);
        assertEquals(1, seatList.size());
        assertEquals(200, response.getStatus());

//        response = target.path("api").path("seats")
//                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
//        assertEquals(404, response.getStatus());

        //Sprzatanie
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void seatControllerGetSeatTest() {
        Seat tmp = new Seat(2, 5, Hall.B);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);
        assertEquals(201, response.getStatus());

        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
        Seat seat = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).get(Seat.class);
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).get();

        assertNotNull(seat);
        assertEquals(5, seat.getColumn());
        assertEquals(Hall.B, seat.getHall());
        assertEquals(2, seat.getRow());
        assertEquals(200, response.getStatus());

        //Sprzatanie
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());

    }

    @Test
    public void postSeatTest() {
        Seat tmp = new Seat(1, 1, Hall.A);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
        assertNotNull(seatList);
        assertEquals(0, seatList.size());

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);
        seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});

        assertEquals(201, response.getStatus());
        assertEquals(1, seatList.size());

        //Sprzatanie
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void deleteSeatTest() {
        Seat tmp = new Seat(1, 1, Hall.A);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);
        assertEquals(201, response.getStatus());

        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        assertEquals(1, seatList.size());

        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId())).request(MediaType.APPLICATION_JSON).delete();
        List<Seat> seatListAfterDelete = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        assertEquals(200, response.getStatus());
        assertEquals(0, seatListAfterDelete.size());
    }

    @Test
    public void testPutSeat() {
        Seat tmp = new Seat(5, 3, Hall.A);
        Seat tmp2 = new Seat(6, 4, Hall.B);


        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);
        assertEquals(201, response.getStatus());

        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        assertEquals(3, seatList.get(0).getColumn());
        assertEquals(Hall.A, seatList.get(0).getHall());
        assertEquals(5, seatList.get(0).getRow());

        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).put(Entity.json(tmp2));
        seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});


        assertEquals(200, response.getStatus());
        assertEquals(4, seatList.get(0).getColumn());
        assertEquals(Hall.B, seatList.get(0).getHall());
        assertEquals(6, seatList.get(0).getRow());

        //Sprzatanie
        response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId()))
                .request(MediaType.APPLICATION_JSON).delete();
        assertEquals(200, response.getStatus());
    }
}
