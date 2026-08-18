package specs.login;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseResponseSpec;


public class LoginSpec {

    public static ResponseSpecification successfulLoginResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/successful_login_response_schema.json"))
            .expectBody("access", notNullValue())
            .expectBody("refresh", notNullValue())
            .build();

    public static ResponseSpecification wrongCredentialsLoginResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(401)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/wrong_login_response_schema.json"))
            .expectBody("detail", notNullValue())
            .build();

    public static ResponseSpecification emptyCredentialsLoginResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/empty_login_response_schema.json"))
            .expectBody("username", notNullValue())
            .build();

    public static ResponseSpecification emptyCredentialsPasswordResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/empty_password_response_schema.json"))
            .expectBody("password", notNullValue())
            .build();

    public static ResponseSpecification emptyCredentialsResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/empty_password_and_login_response_schema.json"))
            .expectBody("username", notNullValue())
            .expectBody("password",notNullValue())
            .build();
}
