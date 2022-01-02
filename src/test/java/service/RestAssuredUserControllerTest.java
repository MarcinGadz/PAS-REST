package service;

import com.google.gson.Gson;
import com.pas.app.model.Hall;
import com.pas.app.model.Seat;
import com.pas.app.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredUserControllerTest {
    User tmp = new User("Nowo", "Dodany", "nowylogin");

    @Test
    public void userControllerGetListTest() {
        RestAssured.baseURI = "http://localhost:8081";

        //To trzeba tu inaczej, bo w poscie dodaje i zalezy od kolejnosci testow
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

    //TODO po zrobieniu testów ticketa dokonczyc dwa testy ponizej
    @Test
    public void userControllerGetActiveTest() {
        RestAssured.baseURI = "http://localhost:8081";

//        get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003/active")
//                .then().statusCode(200).assertThat()
//                .body("size()", equalTo(1));
    }

    @Test
    public void userControllerGetInactiveTest() {
        RestAssured.baseURI = "http://localhost:8081";

//        get("/api/user/a3bf9df2-6b2b-11ec-90d6-0242ac120003/active")
//                .then().statusCode(200).assertThat()
//                .body("size()", equalTo(1));
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
