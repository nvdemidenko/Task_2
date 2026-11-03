package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public Response getIngredients() {
        return given()
                .when()
                .get(BASE_URL + "/ingredients");
    }
}