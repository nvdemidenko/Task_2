package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import stellarburgers.model.OrderRequest;
import client.AuthClient;
import client.OrdersClient;
import stellarburgers.model.RegisterRequest;
import utils.IngredientStorage;
import utils.RandomData;

import java.util.List;

@Epic("Заказы")
@Feature("Создание заказа")
public class OrderTest {
    private AuthClient authClient;
    private OrdersClient ordersClient;
    private String accessToken;

    @BeforeEach
    void setUp() {
        /* Сначала получаем токен! */
        authClient = new AuthClient();
        RegisterRequest user = new RegisterRequest(
                RandomData.generateEmail(),
                RandomData.generatePassword(),
                RandomData.generateName()
        );

        // Регистрация и получение токена
        accessToken = authClient.register(user).body().jsonPath().getString("accessToken");

        IngredientStorage.loadIngredients();

    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Step("Создаем заказ с двумя случайными ингредиентами")
    void createOrderWithAuth() {
        List<String> ingredients = IngredientStorage.getRandomIngredientIds(2); // Берем 2 ID
        OrderRequest request = new OrderRequest(ingredients);

        ordersClient.createOrder("Bearer " + accessToken, request)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Step("Пытаемся создать заказ без токена")
    void createOrderWithoutAuth() {
        List<String> ingredients = List.of(IngredientStorage.getRandomIngredientId()); // Любой один валидный ID

        ordersClient.createOrder(null, new OrderRequest(ingredients)) // Без заголовка Authorization
                .then()
                .statusCode(401)
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Step("Отправляем пустой массив ингредиентов")
    void createOrderWithoutIngredients() {
        OrderRequest request = new OrderRequest(List.of()); // Пустой список

        ordersClient.createOrder("Bearer " + accessToken, request)
                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Step("Отправляем несуществующий ID ингредиента")
    void createOrderWithInvalidIngredient() {
        OrderRequest request = new OrderRequest(List.of("invalid_hash_string_that_does_not_exist"));

        ordersClient.createOrder("Bearer " + accessToken, request)
                .then()
                .statusCode(500);
    }
}