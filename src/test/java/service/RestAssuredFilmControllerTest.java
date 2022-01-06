package service;

import com.google.gson.Gson;
import com.pas.app.model.Film;
import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
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

public class RestAssuredFilmControllerTest {
    Film tmp = new Film(
            "Title",
            "Genre",
            LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(14, 45)),
            LocalDateTime.of(LocalDate.of(1999, 7, 16), LocalTime.of(16, 15)),
            BigDecimal.valueOf(100)
    );
    Film tmp2 = new Film(
            "Title2",
            "Genre2",
            LocalDateTime.of(LocalDate.of(2000, 7, 16), LocalTime.of(14, 45)),
            LocalDateTime.of(LocalDate.of(2000, 7, 16), LocalTime.of(16, 15)),
            BigDecimal.valueOf(100)
    );
    Gson gson = GsonLocalDateTime.getGsonSerializer();

    @Test
    public void filmControllerGetListTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp))
                .when().post("/api/film")
                .then().statusCode(201).extract().response();
        ;

        get("/api/film").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void filmControllerGetTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp))
                .when().post("/api/film")
                .then().statusCode(201).extract().response();

        get("/api/film/" + response.jsonPath().getString("id")).then().statusCode(200)
                .assertThat().body("title", equalTo("Title"),
                        "genre", equalTo("Genre"),
                        "beginTime", equalTo("1999-07-16T14:45:00"),
                        "endTime", equalTo("1999-07-16T16:15:00"),
                        "basePrice", equalTo(100));

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void filmControllerPostTest() {
        RestAssured.baseURI = "http://localhost:8081";

        get("/api/film").then().statusCode(200).assertThat()
                .body("size()", equalTo(0)).extract().response();

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp))
                .when().post("/api/film")
                .then().statusCode(201)
                .assertThat().body("title", equalTo("Title"),
                        "genre", equalTo("Genre"),
                        "beginTime", equalTo("1999-07-16T14:45:00"),
                        "endTime", equalTo("1999-07-16T16:15:00"),
                        "basePrice", equalTo(100)).extract().response();

        get("/api/film").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void filmControllerPutTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Response responseBeforePut = given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp))
                .when().post("/api/film")
                .then().statusCode(201)
                .assertThat().body("title", equalTo("Title"),
                        "genre", equalTo("Genre"),
                        "beginTime", equalTo("1999-07-16T14:45:00"),
                        "endTime", equalTo("1999-07-16T16:15:00"),
                        "basePrice", equalTo(100)).extract().response();

        given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp2))
                .when().put("/api/film/" + responseBeforePut.jsonPath().getString("id"))
                .then().statusCode(202)
                .assertThat().body("title", equalTo("Title2"),
                        "genre", equalTo("Genre2"),
                        "beginTime", equalTo("2000-07-16T14:45:00"),
                        "endTime", equalTo("2000-07-16T16:15:00"),
                        "basePrice", equalTo(100)).extract().response();

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + responseBeforePut.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void filmControllerDeleteTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Response response = given().header("Content-type", "application/json")
                .and().body(gson.toJson(tmp))
                .when().post("/api/film")
                .then().statusCode(201).extract().response();

        get("/api/film").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/film/" + response.jsonPath().getString("id"))
                .then().statusCode(202);

        get("/api/film").then().statusCode(200).assertThat()
                .body("size()", equalTo(0));
    }
}
