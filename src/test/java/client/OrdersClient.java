package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stellarburgers.model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public Response createOrder(String accessToken, OrderRequest request) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL + "/orders");
    }
}