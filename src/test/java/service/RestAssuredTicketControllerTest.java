package service;

import com.google.gson.Gson;
import com.pas.app.model.*;
import com.pas.app.utils.GsonLocalDateTime;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTicketControllerTest {
    Seat seat = new Seat(2, 5, Hall.B);
    Film film = new Film(
            "Title",
            "Genre",
            LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(14, 45)),
            LocalDateTime.of(LocalDate.of(2023, 7, 16), LocalTime.of(16, 15)),
            BigDecimal.valueOf(100)
    );
    String seatId;
    String filmId;
    Gson gson = GsonLocalDateTime.getGsonSerializer();

    private String postTicket() {
        Response tempSeat = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(seat)).when().post("/api/seat")
                .then().statusCode(201).extract().response();
        Response tempUser = get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32").then().statusCode(200)
                .extract().response();
        Response tempFilm = given().header("Content-type", "application/json")
                .and().body(gson.toJson(film)).when().post("/api/film")
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
    public void ticketControllerGetListTest() {
        RestAssured.baseURI = "http://localhost:8080/pas-rest-1.0-SNAPSHOT/";

        String ticketId = postTicket();
        get("/api/ticket").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));
        cleanTicket(ticketId);
    }

    @Test
    public void ticketControllerGetTest() {
        RestAssured.baseURI = "http://localhost:8080/pas-rest-1.0-SNAPSHOT/";

        String ticketId = postTicket();
        get("/api/ticket/" + ticketId).then().statusCode(200).assertThat()
                .body(
                        "user.firstName", equalTo("John"),
                        "film.title", equalTo("Title"),
                        "seat.row", equalTo(2));
        cleanTicket(ticketId);
    }

    @Test
    public void ticketControllerPostTest() {
        RestAssured.baseURI = "http://localhost:8080/pas-rest-1.0-SNAPSHOT/";

        get("/api/ticket").then().statusCode(200).assertThat()
                .body("size()", equalTo(0)).extract().response();

        Response tempSeat = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(seat)).when().post("/api/seat")
                .then().statusCode(201).extract().response();
        Response tempUser = get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32").then().statusCode(200)
                .extract().response();
        Response tempFilm = given().header("Content-type", "application/json")
                .and().body(gson.toJson(film)).when().post("/api/film")
                .then().statusCode(201).extract().response();

        Ticket ticket = new Ticket(tempUser.body().as(User.class),
                tempFilm.body().as(Film.class), tempSeat.body().as(Seat.class));

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(ticket)).when().post("/api/ticket")
                .then().statusCode(201).extract().response();

        seatId = tempSeat.jsonPath().getString("id");
        filmId = tempFilm.jsonPath().getString("id");

        get("/api/ticket").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        cleanTicket(response.jsonPath().getString("id"));
    }

    @Test
    public void ticketControllerPutTest() {
        RestAssured.baseURI = "http://localhost:8080/pas-rest-1.0-SNAPSHOT/";

        String ticketId = postTicket();

        Seat seat = new Seat(2, 6, Hall.B);
        Response tempSeat = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(seat)).when().post("/api/seat")
                .then().statusCode(201).extract().response();
        Response tempUser = get("/api/user/149bf059-f6a3-4c7b-8bfc-a577a485ac32").then().statusCode(200)
                .extract().response();
        Response tempFilm = get("/api/film/" + filmId).then().statusCode(200)
                .extract().response();

        Ticket ticket = new Ticket(tempUser.body().as(User.class),
                tempFilm.body().as(Film.class), tempSeat.body().as(Seat.class));

        given().header("Content-type", "application/json")
                .and().body(gson.toJson(ticket))
                .when().put("/api/ticket/" + ticketId)
                .then().statusCode(202)
                .assertThat().body(
                        "user.firstName", equalTo("John"),
                        "film.title", equalTo("Title"),
                        "seat.row", equalTo(2));

        cleanTicket(ticketId);
    }

    @Test
    public void ticketControllerWrongAllocationTest() {
        RestAssured.baseURI = "http://localhost:8080/pas-rest-1.0-SNAPSHOT/";

        String ticketId = postTicket();

        Response tempSeat = get("/api/seat/" + seatId).then().statusCode(200)
                .extract().response();
        Response tempUser = get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003").then().statusCode(200)
                .extract().response();
        Response tempFilm = get("/api/film/" + filmId).then().statusCode(200)
                .extract().response();

        Ticket ticket = new Ticket(tempUser.body().as(User.class),
                tempFilm.body().as(Film.class), tempSeat.body().as(Seat.class));

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(ticket)).when().post("/api/ticket")
                .then().statusCode(400).extract().response();

        cleanTicket(ticketId);
    }
}
