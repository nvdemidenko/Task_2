package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stellarburgers.model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public Response createOrder(String accessToken, OrderRequest request) {
        RequestSpecification reqSpec = given();
        if (accessToken != null && !accessToken.isEmpty()) {
            reqSpec = reqSpec.header("Authorization", accessToken);
        }
        return reqSpec
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL + "/orders");
    }
}