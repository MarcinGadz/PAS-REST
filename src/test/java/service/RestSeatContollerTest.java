package service;

import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestSeatContollerTest {

    @Test
    public void seatControllerTest() {
        Seat tmp = new Seat(4, 2, Hall.C);

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        Client client = clientBuilder.build();

        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
        List<Seat> seatList = target.path("api").path("seat").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Seat>>(){});
//        Seat seat = target.path("api").path("seat").path(String.valueOf(tmp.getId())).request(MediaType.APPLICATION_JSON).get(Seat.class);

        assertNotNull(seatList);
        assertEquals(3, seatList.size());
        assertEquals(2, seatList.get(0).getColumn());
        assertEquals(Hall.A, seatList.get(0).getHall());
        assertEquals(1, seatList.get(0).getRow());
    }

}
