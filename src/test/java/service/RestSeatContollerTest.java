package service;

import com.pas.app.model.Film;
import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
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
    public void seatRestResponseTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void seatControllerGetListTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();
        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});

        assertNotNull(seatList);
        assertEquals(3, seatList.size());
    }

    @Test
    public void seatControllerGetSeatTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        List<Seat> seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
        Seat seat = target.path("api").path("seat").path(String.valueOf(seatList.get(1).getId()))
                .request(MediaType.APPLICATION_JSON).get(Seat.class);

        assertNotNull(seat);
        assertEquals(1, seat.getColumn());
        assertEquals(Hall.A, seat.getHall());
        assertEquals(1, seat.getRow());
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
        assertEquals(3, seatList.size());

        Response response = target.path("api").path("seat").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(tmp), Response.class);
        seatList = target.path("api").path("seat")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});

        assertEquals(4, seatList.size());
    }

    @Test
    public void deleteSeatTest() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        List<Seat> seatList = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});

        assertEquals(3, seatList.size());

        Response response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId())).request(MediaType.APPLICATION_JSON).delete();
        List<Seat> seatListAfterDelete = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        assertEquals(3, seatListAfterDelete.size());
    }

    @Test
    public void testPutSeat() {
        Seat tmp = new Seat(1, 1, Hall.A);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");

        List<Seat> seatList = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        target.path("api").path("seat").path(String.valueOf(seatList.get(1).getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(tmp));

        assertEquals(1, seatList.get(1).getColumn());
        assertEquals(Hall.A, seatList.get(1).getHall());
        assertEquals(1, seatList.get(1).getRow());

        Seat tmp1 = new Seat(-11, 1, Hall.A);
        List<Seat> seatList1 = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>() {});
        Response response = target.path("api").path("seat").path(String.valueOf(seatList.get(0).getId())).request(MediaType.APPLICATION_JSON).put(Entity.json(tmp1));
        System.out.println(response.getStatus());
    }
}
