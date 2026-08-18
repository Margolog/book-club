package specs.logout;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseResponseSpec;

public class LogoutSpec {

    public static ResponseSpecification successfulLogoutResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification logoutWithoutTokenRequestSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/logout_without_token_response_schema.json"))
            .expectBody("refresh", notNullValue())
            .build();

    public static ResponseSpecification logoutWithWrongTokenRequestSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(401)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/logout_with_wrong_token_response_schema.json"))
            .expectBody("detail", notNullValue())
            .expectBody("code", notNullValue())
            .build();
}
