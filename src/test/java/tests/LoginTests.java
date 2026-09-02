package tests;

import io.qameta.allure.Feature;
import models.login.*;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.UserData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.UserData.*;

@Feature("Авторизация")
public class LoginTests extends BaseTest {

    UserData userData;

    @BeforeEach
    public void prepareTestData() {
        userData = new UserData();
    }

    @Test
    @DisplayName("Успешный логин возвращает access и refresh токены")
    public void successfulLoginTest() {
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        SuccessfulLoginResponseModel loginResponse =
                api.auth.login(new LoginBodyModel(userData.username, userData.password));

        step("Проверить access и refresh токены", () -> {
            String actualAccess = loginResponse.access();
            String actualRefresh = loginResponse.refresh();

            assertThat(actualAccess).startsWith(LOGIN_TOKEN_PREFIX);
            assertThat(actualRefresh).startsWith(LOGIN_TOKEN_PREFIX);
            assertThat(actualAccess).isNotEqualTo(actualRefresh);
        });
    }

    @Test
    @DisplayName("Логин с неверным паролем возвращает ошибку")
    public void wrongPasswordLoginTest() {
        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(
                new LoginBodyModel(LOGIN_USERNAME, WRONG_PASSWORD));

        step("Проверить сообщение об ошибке авторизации", () ->
                assertThat(loginResponse.detail()).isEqualTo(LOGIN_WRONG_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Логин с несуществующим username возвращает ошибку")
    public void wrongUserNameLoginTest() {
        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(
                new LoginBodyModel(userData.username, LOGIN_PASSWORD));

        step("Проверить сообщение об ошибке авторизации", () ->
                assertThat(loginResponse.detail()).isEqualTo(LOGIN_WRONG_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Логин без username возвращает ошибку в поле username")
    public void withoutUserNameTest() {
        EmptyLoginResponseModel loginResponse = api.auth.emptyLoginCredentials(
                new LoginBodyModel("", LOGIN_PASSWORD));

        step("Проверить ошибку в поле username", () ->
                assertThat(loginResponse.username()).containsExactly(EMPTY_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Логин без password возвращает ошибку в поле password")
    public void withoutPasswordTest() {
        EmptyPasswordResponseModel loginResponse = api.auth.emptyPasswordResponseModel(
                new LoginBodyModel(LOGIN_USERNAME, ""));

        step("Проверить ошибку в поле password", () ->
                assertThat(loginResponse.password()).containsExactly(EMPTY_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Логин без username и password возвращает две ошибки")
    public void withoutPasswordAndLoginTest() {
        EmptyPasswordAndLoginResponseModel response = api.auth.emptyPasswordAndLogin(
                new LoginBodyModel("", ""));

        step("Проверить ошибки в полях username и password", () -> {
            assertThat(response.username()).containsExactly(EMPTY_CREDENTIALS_ERROR);
            assertThat(response.password()).containsExactly(EMPTY_CREDENTIALS_ERROR);
        });
    }
}
