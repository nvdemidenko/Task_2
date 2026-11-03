package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.AuthClient;
import stellarburgers.model.RegisterRequest;
import utils.RandomData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Авторизация")
@Feature("Вход в систему")
public class LoginTest {

    private AuthClient authClient;
    private RegisterRequest user;

    @BeforeEach
    void setUp() {
        authClient = new AuthClient();
        user = new RegisterRequest(
                RandomData.generateEmail(),
                RandomData.generatePassword(),
                RandomData.generateName()
        );
        // Предусловие: пользователь должен существовать
        authClient.register(user);
    }

    @Test
    @DisplayName("Успешный вход под существующим пользователем")
    @Description("Проверка получения токена при верном логине и пароле")
    @Step("Выполняем POST запрос на /api/auth/login")
    void loginSuccess() {
        authClient.login(user)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Проверка ошибки 401 при несовпадении пароля")
    @Step("Пытаемся войти с неправильным паролем")
    void loginWrongPassword() {
        RegisterRequest wrongPass = new RegisterRequest(user.getEmail(), "WrongPass123", user.getName());

        authClient.login(wrongPass)
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}