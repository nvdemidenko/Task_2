package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stellarburgers.model.RegisterRequest;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public Response register(RegisterRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL + "/auth/register");
    }

    public Response login(RegisterRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL + "/auth/login");
    }
}