package service;

import com.google.gson.Gson;
import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredSeatControllerTest {

    @Test
    public void seatControllerGetListTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, 5, Hall.B);
        Response response =  given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(201).extract().response();

        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/seat/"+response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void seatControllerGetTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, 5, Hall.B);
        Response response = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(201).extract().response();

        get("/api/seat/"+response.jsonPath().getString("id")).then().statusCode(200)
                .assertThat().body("row", equalTo(2),
                        "column", equalTo(5),
                        "hall", equalTo("B"));

        given().header("Content-type", "application/json")
                .when().delete("/api/seat/"+response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void seatControllerPostTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, 5, Hall.B);
        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(0)).extract().response();

        Response response = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(201)
                .assertThat().body("row", equalTo(2),
                        "column", equalTo(5),
                        "hall", equalTo("B")).extract().response();

        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/seat/"+response.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void seatControllerPutTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, 5, Hall.B);
        Seat tmp2 = new Seat(3, 6, Hall.C);
        Response responseBeforePut = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(201)
                .assertThat().body("row", equalTo(2),
                        "column", equalTo(5),
                        "hall", equalTo("B")).extract().response();

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp2))
                .when().put("/api/seat/" + responseBeforePut.jsonPath().getString("id"))
                .then().statusCode(202)
                .assertThat().body("row", equalTo(3),
                        "column", equalTo(6),
                        "hall", equalTo("C")).extract().response();

        given().header("Content-type", "application/json")
                .when().delete("/api/seat/"+responseBeforePut.jsonPath().getString("id"))
                .then().statusCode(202);
    }

    @Test
    public void seatControllerDeleteTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, 5, Hall.B);
        Response response = given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(201).extract().response();

        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(1));

        given().header("Content-type", "application/json")
                .when().delete("/api/seat/"+response.jsonPath().getString("id"))
                .then().statusCode(202);

        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(0));
    }

    @Test
    public void seatControllerWrongPostTest() {
        RestAssured.baseURI = "http://localhost:8081";

        Seat tmp = new Seat(2, -5, Hall.B);
        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(0)).extract().response();

        given().header("Content-type", "application/json")
                .and().body(new Gson().toJson(tmp))
                .when().post("/api/seat")
                .then().statusCode(400);

        get("/api/seat").then().statusCode(200).assertThat()
                .body("size()", equalTo(0));
    }
}
