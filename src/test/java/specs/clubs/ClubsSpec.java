package specs.clubs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseResponseSpec;

public class ClubsSpec {

    public static ResponseSpecification successfulCreateClubsResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/successful_create_clubs_response_schema.json"))
            .expectBody("id", notNullValue())
            .build();

    public static ResponseSpecification successfulGetClubsResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/successful_get_clubs_response_schema.json"))
            .expectBody("count", notNullValue())
            .expectBody("results", notNullValue())
            .build();

    public static ResponseSpecification successfulUpdateClubsResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/clubs/successful_patch_clubs_response_schema.json"))
            .build();

    public static ResponseSpecification successfulDeleteClubsResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification notFoundClubResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(404)
            .build();
}
