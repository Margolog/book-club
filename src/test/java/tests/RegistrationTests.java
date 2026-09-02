package tests;

import io.qameta.allure.Feature;
import models.registration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.UserData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.UserData.*;

@Feature("Регистрация")
public class RegistrationTests extends BaseTest {

    UserData userData;

    @BeforeEach
    public void prepareTestData() {
        userData = new UserData();
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void successfulRegistrationTest() {
        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        step("Проверить данные зарегистрированного пользователя", () -> {
            assertThat(registrationResponse.id()).isGreaterThan(0);
            assertThat(registrationResponse.username()).isEqualTo(userData.username);
            assertThat(registrationResponse.firstName()).isEmpty();
            assertThat(registrationResponse.lastName()).isEmpty();
            assertThat(registrationResponse.email()).isEmpty();
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });
    }

    @Test
    @DisplayName("Повторная регистрация существующего пользователя возвращает ошибку")
    public void existingUserWrongRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(userData.username, userData.password);

        SuccessfulRegistrationResponseModel firstRegistrationResponse =
                api.users.register(registrationData);

        step("Проверить успешную первичную регистрацию", () ->
                assertThat(firstRegistrationResponse.username()).isEqualTo(userData.username));

        ExistingUserResponseModel secondRegistrationResponse =
                api.users.registerExistingUser(registrationData);

        step("Проверить ошибку повторной регистрации", () ->
                assertThat(secondRegistrationResponse.username())
                        .containsExactly(REGISTRATION_EXISTING_USER_ERROR));
    }

    @Test
    @DisplayName("Регистрация без password возвращает ошибку в поле password")
    public void registrationWithoutPasswordTest() {
        RegistrationPasswordErrorResponseModel registrationWithoutPassword =
                api.users.registrationWithoutPassword(new RegistrationBodyModel(userData.username, ""));

        step("Проверить ошибку в поле password", () ->
                assertThat(registrationWithoutPassword.password())
                        .containsExactly(EMPTY_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Регистрация без username возвращает ошибку в поле username")
    public void registrationWithoutUsernameTest() {
        RegistrationWithoutUserNameResponseModel registrationWithoutUserName =
                api.users.registrationWithoutUserName(new RegistrationBodyModel("", userData.password));
        step("Проверить ошибку в поле username", () ->
                assertThat(registrationWithoutUserName.username())
                        .containsExactly(EMPTY_CREDENTIALS_ERROR));
    }

    @Test
    @DisplayName("Регистрация без username и password возвращает две ошибки")
    public void registrationWithoutUsernameAndPasswordTest() {
        RegistrationWithoutUsernameAndPasswordResponseModel response =
                api.users.registrationWithoutUsernameAndPassword(new RegistrationBodyModel("", ""));

        step("Проверить ошибки в полях username и password", () -> {
            assertThat(response.username()).containsExactly(EMPTY_CREDENTIALS_ERROR);
            assertThat(response.password()).containsExactly(EMPTY_CREDENTIALS_ERROR);
        });
    }

    @Test
    @DisplayName("Регистрация с password длиннее 128 символов возвращает ошибку")
    public void registrationWithLongPasswordTest() {
        RegistrationPasswordErrorResponseModel response =
                api.users.registrationWithLongPassword(
                        new RegistrationBodyModel(userData.username, userData.longPassword));

        step("Проверить ошибку ограничения длины password", () ->
                assertThat(response.password()).containsExactly(LONG_PASSWORD_ERROR));
    }

    @Test
    @DisplayName("Регистрация с null в username и password возвращает две ошибки")
    public void registrationWithNullUsernameAndPasswordTest() {
        RegistrationWithoutUsernameAndPasswordResponseModel response =
                api.users.registrationWithoutUsernameAndPassword(new RegistrationBodyModel(null, null));

        step("Проверить ошибки для null в username и password", () -> {
            assertThat(response.username()).containsExactly(NULL_CREDENTIALS_ERROR);
            assertThat(response.password()).containsExactly(NULL_CREDENTIALS_ERROR);
        });
    }
}
