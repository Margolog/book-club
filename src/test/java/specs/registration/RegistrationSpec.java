package specs.registration;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseResponseSpec;

public class RegistrationSpec {

    public static ResponseSpecification successfulRegistrationResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/registration/successful_registration_response_schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("username", notNullValue())
            .expectBody("remoteAddr", notNullValue())
            .build();

    public static ResponseSpecification existingUserRegistrationResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/registration/existing_user_registration_response_schema.json"))
            .expectBody("username", notNullValue())
            .build();

    public static ResponseSpecification registrationPasswordErrorResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/registration/registration_password_error_response_schema.json"))
            .expectBody("password", notNullValue())
            .build();

    public static ResponseSpecification registrationWithoutUserNameResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/registration/registration_without_username_response_schema.json"))
            .expectBody("username", notNullValue())
            .build();

    public static ResponseSpecification registrationWithoutUsernameAndPasswordResponseSpec =
            new ResponseSpecBuilder()
                    .addResponseSpecification(baseResponseSpec)
                    .expectStatusCode(400)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/registration/registration_without_username_and_password_response_schema.json"))
                    .expectBody("username", notNullValue())
                    .expectBody("password", notNullValue())
                    .build();
}
