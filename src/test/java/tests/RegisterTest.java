package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.AuthClient;
import stellarburgers.model.RegisterRequest;
import utils.RandomData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Регистрация")
public class RegisterTest {
    private AuthClient authClient;
    private RegisterRequest validUser;

    @BeforeEach
    void setUp() {
        authClient = new AuthClient();
        validUser = new RegisterRequest(
                RandomData.generateEmail(),
                RandomData.generatePassword(),
                RandomData.generateName()
        );
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешного создания нового пользователя")
    @Step("Выполняем запрос на регистрацию с валидными данными")
    void registerUniqueUser() {
        authClient.register(validUser)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(validUser.getEmail()))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка ошибки при попытке регистрации существующего email")
    @Step("Регистрируем пользователя, затем пытаемся зарегистрировать его снова")
    void registerExistingUser() {
        authClient.register(validUser);
        authClient.register(validUser)
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Проверка ошибки при отсутствии email")
    @Step("Выполняем запрос на регистрацию без email")
    void registerUserWithoutRequiredField() {
        RegisterRequest invalidUser = new RegisterRequest(null, validUser.getPassword(), validUser.getName());
        authClient.register(invalidUser)
                .then()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}