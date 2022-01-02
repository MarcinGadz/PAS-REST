//package service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//import com.google.gson.Gson;
//import com.pas.app.model.Film;
//import com.pas.app.model.Role;
//import com.pas.app.model.User;
//import com.pas.app.utils.GsonLocalDateTime;
//import org.glassfish.jersey.client.ClientConfig;
//import org.junit.jupiter.api.Test;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RestUserControllerTests {
//
//    @Test
//    public void userControllerGetListTest() {
//        //dodanie obiektu do testow
////        User user = new User("First", "Last",)
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        Gson gson = GsonLocalDateTime.getGsonSerializer();
//
//        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
//
//        Client client = ClientBuilder.newClient(new ClientConfig().register(provider));
//        WebTarget target = client.target("http://localhost:8080/pas-rest-1.0-SNAPSHOT/");
//
//        User user = new User("firstname", "lastname", "login");
//        user.setActive(true);
//        Response response = target.path("api").path("user").request(MediaType.APPLICATION_JSON)
//                .post(Entity.json(user), Response.class);
//        assertEquals(201, response.getStatus());
//
//        List<User> userList = target.path("api").path("user").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
//        response = target.path("api").path("user").request(MediaType.APPLICATION_JSON).get();
//        assertEquals(200, response.getStatus());
//
//        User userInResponse = target.path("api").path("user").path(String.valueOf(userList.get(0).getId()))
//                .request(MediaType.APPLICATION_JSON).get(User.class);
//        response = target.path("api").path("user").path(String.valueOf(userList.get(0).getId()))
//                .request(MediaType.APPLICATION_JSON).get();
//        assertEquals(200, response.getStatus());
//
//        assertNotNull(userInResponse);
//        assertEquals("login", userInResponse.getLogin());
//        assertEquals("firstname", userInResponse.getFirstName());
//        assertEquals("lastname", userInResponse.getLastName());
//        assertTrue(userInResponse.isActive());
//        assertEquals(Role.ROLE_USER, userInResponse.getRole());
//
//        //sprzatanie po tescie
//        response = target.path("api").path("user").path(String.valueOf(userList.get(0).getId()))
//                .request(MediaType.APPLICATION_JSON).delete();
//        assertEquals(200, response.getStatus());
//        response = target.path("api").path("user").path(String.valueOf(userList.get(0).getId()))
//                .request(MediaType.APPLICATION_JSON).get();
//        assertFalse(userInResponse.isActive());
//    }
//}
