//package service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.pas.app.model.*;
//
//import org.junit.jupiter.api.Test;
//
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class RestTicketControllerTest {
//
//    @Test
//    public void ticketRestResponseTest() {
//        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
//        Client client = clientBuilder.build();
//
//        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
//        Response response = target.path("api").path("ticket").request(MediaType.APPLICATION_JSON).get();
//
//        assertEquals(200, response.getStatus());
//    }
//
////    @Test
////    public void ticketControllerGetListTest() {
////        User tempUser = new User("Michal", "Tosik", "mtosik");
////        Film tempFilm = new Film(
////                "Title",
////                "Genre",
////                LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(14, 45)),
////                LocalDateTime.of(LocalDate.of(1999,7,16), LocalTime.of(16, 15)),
////                BigDecimal.valueOf(100)
////        );
////        Seat tempSeat = new Seat(1, 1, Hall.A);
////        Ticket tempTicket = new Ticket("123", tempUser, tempFilm, tempSeat);
////
////        ObjectMapper mapper = new ObjectMapper();
////        mapper.registerModule(new JavaTimeModule());
////
////        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
////
////        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
////
////        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT");
////        Response response = target.path("api").path("ticket").request().post(Entity.json(tempTicket));
////
////        List<Ticket> ticketList = target.path("api").path("ticket").
////                request(MediaType.APPLICATION_JSON).get(new GenericType<List<Ticket>>(){});
////        System.out.println(ticketList);
////        System.out.println(response.getStatusInfo());
////
////        Ticket ticket = target.path("api").path("ticket").path(String.valueOf(ticketList.get(0).getId())).
////                request(MediaType.APPLICATION_JSON).get(Ticket.class);
////
////        assertNotNull(ticketList);
////        assertNotNull(ticket);
////
////        assertEquals(1, ticketList.size());
////    }
//
//    @Test
//    public void ticketControllerGetTicketTest() {
//
//    }
//}
