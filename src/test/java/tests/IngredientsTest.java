package tests;

import io.qameta.allure.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.IngredientsClient;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

@Epic("Ингредиенты")
public class IngredientsTest {

    private IngredientsClient ingredientsClient;

    @BeforeEach
    void setUp() {
        ingredientsClient = new IngredientsClient();
    }

    @Test
    @DisplayName("Получение списка всех ингредиентов") void getIngredients() {
        ingredientsClient.getIngredients()
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.size()", greaterThanOrEqualTo(1));
        }
}