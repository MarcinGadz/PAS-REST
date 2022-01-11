package service;

import com.google.gson.Gson;
import com.pas.app.model.*;
import com.pas.app.utils.GsonLocalDateTime;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredUserControllerTest {
    User tmp = new User("Nowo", "Dodany", "nowylogin");

    Seat seat = new Seat(2, 5, Hall.B);
    Film film = new Film(
            "Title",
            "Genre",
            LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(14, 45)),
            LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(16, 15)),
            BigDecimal.valueOf(100)
    );
    Film film2 = new Film(
            "Title",
            "Genre",
            LocalDateTime.of(LocalDate.of(2010, 7, 16), LocalTime.of(14, 45)),
            LocalDateTime.of(LocalDate.of(2010, 7, 16), LocalTime.of(16, 15)),
            BigDecimal.valueOf(100)
    );
    String seatId;
    String filmId;
    Gson gson = GsonLocalDateTime.getGsonSerializer();

    private String postTicket(Film f) {
        Response tempSeat = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(seat)).when().post("/api/seat")
                .then().statusCode(201).extract().response();
        Response tempUser = get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32").then().statusCode(200)
                .extract().response();
        Response tempFilm = given().header("Content-type", "application/json")
                .and().body(gson.toJson(f)).when().post("/api/film")
                .then().statusCode(201).extract().response();

        Ticket ticket = new Ticket(tempUser.body().as(User.class),
                tempFilm.body().as(Film.class), tempSeat.body().as(Seat.class));
//        ticket.setId(UUID.fromString("22b24cd6-6b45-11ec-90d6-0242ac120003"));

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(ticket)).when().post("/api/ticket")
                .then().statusCode(201).extract().response();

        seatId = tempSeat.jsonPath().getString("id");
        filmId = tempFilm.jsonPath().getString("id");

        return response.jsonPath().getString("id");
    }
    private void cleanTicket(String id) {
        given().header("Content-type", "application/json")
                .when().delete("/api/ticket/" + id)
                .then().statusCode(202);
        given().header("Content-type", "application/json")
                .when().delete("/api/seat/" + seatId)
                .then().statusCode(202);

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + filmId)
                .then().statusCode(202);
    }

    @Test
    public void userControllerGetListTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user").then().statusCode(200).assertThat()
                .body("size()", equalTo(3));
    }

    @Test
    public void userControllerGetTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32")
                .then().statusCode(200).assertThat()
                .body("firstName", equalTo("John"),
                        "lastName", equalTo("Doe"),
                        "login", equalTo("JD"),
                        "active", equalTo(true),
                        "role", equalTo("ROLE_USER"));
    }

    @Test
    public void userControllerFindTest() {
        RestAssured.baseURI = "http://localhost:8081";

        given().param("login", "JD").when()
                .get("/api/user/find")
                .then().statusCode(200).assertThat()
                .body("firstName", equalTo("John"),
                        "lastName", equalTo("Doe"),
                        "login", equalTo("JD"),
                        "active", equalTo(true),
                        "role", equalTo("ROLE_USER"));
    }

    @Test
    public void userControllerFindContainingTest() {
        RestAssured.baseURI = "http://localhost:8081";

        given().param("login", "123").when()
                .get("/api/user/findcontaining")
                .then().statusCode(200).assertThat()
                .body("[0].firstName", equalTo("Jan"),
                        "[0].lastName", equalTo("Kowalski"),
                        "[0].login", equalTo("Janko123"),
//                        "[0].active", equalTo(false),
                        "[0].role", equalTo("ROLE_USER"));

        given().param("login", "J").when()
                .get("/api/user/findcontaining")
                .then().statusCode(200).assertThat()
                .body("size()", equalTo(2));
    }

    @Test
    public void userControllerActivateTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/df022edf-bb0a-42c4-a4b7-7c37af542fd9")
                .then().statusCode(200).assertThat()
                .body("active", equalTo(false));

        put("/api/user/df022edf-bb0a-42c4-a4b7-7c37af542fd9/activate")
                .then().statusCode(202);

        get("/api/user/df022edf-bb0a-42c4-a4b7-7c37af542fd9")
                .then().statusCode(200).assertThat()
                .body("active", equalTo(true));

        put("/api/user/df022edf-bb0a-42c4-a4b7-7c37af542fd9/deactivate")
                .then().statusCode(202);
    }

    @Test
    public void userControllerDeactivateTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003")
                .then().statusCode(200).assertThat()
                .body("active", equalTo(true));

        given().contentType(ContentType.JSON)
                .put("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003/deactivate")
                .then().statusCode(202);

        get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003")
                .then().statusCode(200).assertThat()
                .body("active", equalTo(false));

        given().contentType(ContentType.JSON)
                .put("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003/activate")
                .then().statusCode(202);
    }

    @Test
    public void userControllerPostTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user").then().statusCode(200).assertThat()
                .body("size()", equalTo(3));

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/user")
                .then().statusCode(201);

        get("/api/user").then().statusCode(200).assertThat()
                .body("size()", equalTo(4));
    }

    @Test
    public void userControllerUpdateTest() {
        RestAssured.baseURI = "http://localhost:8081";
        User tmp2 = new User("Testname2", "Testsurname2", "Testlogin2");

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp2))
                .when().put("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003")
                .then().statusCode(202)
                .assertThat()
                .body("firstName", equalTo("Testname2"),
                        "lastName", equalTo("Testsurname2"),
                        "login", equalTo("Testlogin2"));
    }

    @Test
    public void userControllerGetActiveTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32/active")
                .then().statusCode(200).assertThat().body("size()", equalTo(0));
        String ticketId = postTicket(film);
        get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32/active")
                .then().statusCode(200).assertThat().body("size()", equalTo(1));
        cleanTicket(ticketId);
    }

    @Test
    public void userControllerGetInactiveTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32/past")
                .then().statusCode(200).assertThat().body("size()", equalTo(0));
        String ticketId = postTicket(film2);
        get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32/past")
                .then().statusCode(200).assertThat().body("size()", equalTo(1));;
        cleanTicket(ticketId);
    }

    @Test
    public void userControllerUniqueIdTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003")
                .then().statusCode(200).assertThat();

        User tmp = new User("Nowo2", "Dodany2", "nowylogin2");
        tmp.setId(UUID.fromString("a3bf9df2-6b2b-11ec-90d6-0242ac120003"));

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/user")
                .then().statusCode(400);
    }

    @Test
    public void userControllerWrongPostTest() {
        RestAssured.baseURI = "http://localhost:8081";

        User tmp = new User("Nowo3", "Dodany3", "");

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/user")
                .then().statusCode(400);
    }
}
