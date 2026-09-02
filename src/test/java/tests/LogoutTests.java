package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.login.LoginBodyModel;
import models.logout.LogoutBodyModel;
import models.logout.LogoutWithWrongTokenBodyModel;
import models.logout.LogoutWithoutTokenBodyModel;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.UserData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static tests.testData.UserData.*;

@Feature("Logout")
public class LogoutTests extends BaseTest {

    UserData userData;

    @BeforeEach
    public void prepareTestData() {
        userData = new UserData();
    }

    @Test
    @Story("Успешный logout")
    @DisplayName("Успешный logout с refresh token")
    public void successfulLogoutTest() {
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        String refreshToken = api.auth.loginAndGetRefreshToken(
                new LoginBodyModel(userData.username, userData.password));

        api.auth.logout(new LogoutBodyModel(refreshToken));
    }

    @Test
    @Story("Негативный кейс на logout")
    @DisplayName("Logout без refresh token возвращает ошибку в поле refresh")
    public void logoutWithoutTokenTest() {
        LogoutWithoutTokenBodyModel response =
                api.auth.logoutWithoutToken(new LogoutBodyModel(""));

        step("Проверить ошибку в поле refresh", () ->
                assertThat(response.refresh()).containsExactly(EMPTY_CREDENTIALS_ERROR));
    }

    @Test
    @Story("Негативный кейс на logout")
    @DisplayName("Logout с невалидным token возвращает ошибку token_not_valid")
    public void logoutWithWrongTokenTest() {
        LogoutWithWrongTokenBodyModel response =
                api.auth.logoutWithWrongToken(new LogoutBodyModel(WRONG_TOKEN));

        step("Проверить ошибку невалидного token", () -> {
            assertThat(response.detail()).isEqualTo(INVALID_TOKEN_DETAIL_ERROR);
            assertThat(response.code()).isEqualTo(INVALID_TOKEN_CODE_ERROR);
        });
    }

    @Test
    @Story("Негативный кейс на logout")
    @DisplayName("Logout с access token вместо refresh token возвращает ошибку")
    public void logoutWithAccessTokenTest() {
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        String accessToken = api.auth.loginAndGetAccessToken(
                new LoginBodyModel(userData.username, userData.password));

        LogoutWithWrongTokenBodyModel response =
                api.auth.logoutWithWrongToken(new LogoutBodyModel(accessToken));

        step("Проверить ошибку token wrong type", () -> {
            assertThat(response.detail()).isEqualTo(TOKEN_WRONG_TYPE_ERROR);
            assertThat(response.code()).isEqualTo(INVALID_TOKEN_CODE_ERROR);
        });
    }
}
